package id.co.mii.serverApp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.Role;
import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.dto.request.UserRequest;
import id.co.mii.serverApp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private EmailService emailService;
    private JobService jobService;
    private EmployeeService employeeService;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!!"));
    }

    public User getbyUsernameOrEmail(String username, String email) {
        return userRepository
                .findByUsernameOrEmployee_Email(username, email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!!"));
    }

    public User create(UserRequest userRequest) {
        Employee employee = modelMapper.map(userRequest, Employee.class);
        User user = modelMapper.map(userRequest, User.class);

        employee.setUser(user);
        user.setEmployee(employee);

        // set default role
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getById(3));
        user.setRoles(roles);
        user.setIsEnabled(false);

        // set Manager
        employee.setManager(employeeService.getById(userRequest.getManagerId()));

        // set Job
        employee.setJob(jobService.getById(userRequest.getJobId()));
        // set password
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Optional<User> saved = Optional.of(userRepository.save(user));

        saved.ifPresent(u -> {
            try {
                String token = UUID.randomUUID().toString();
                verificationTokenService.save(saved.get(), token);
                // send email
                emailService.sendHtmlEmail(u, userRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return saved.get();
    }

    public User update(Integer id, User user) {
        getById(id);
        user.setId(id);
        return userRepository.save(user);
    }

    public User delete(Integer id) {
        User user = getById(id);
        userRepository.delete(user);
        return user;
    }

    public User deleteUser(Integer id) {
        User user = getById(id);
        userRepository.deleteUser(id);
        return user;
    }
}