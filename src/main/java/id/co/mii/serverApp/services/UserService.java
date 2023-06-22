package id.co.mii.serverApp.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!!"));
    }

    public User create(UserRequest userRequest) {
        Employee employee = modelMapper.map(userRequest, Employee.class);
        User user = modelMapper.map(userRequest, User.class);

        employee.setUser(user);
        user.setEmployee(employee);

        // set default role
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getById(2));
        user.setRoles(roles);

        // set password
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return userRepository.save(user);
    }

    public User update(Integer id, User user) {
        getById(id);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User delete(Integer id) {
        User user = getById(id);
        userRepository.delete(user);
        return user;
    }
}