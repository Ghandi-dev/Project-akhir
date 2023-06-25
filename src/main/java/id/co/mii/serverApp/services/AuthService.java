package id.co.mii.serverApp.services;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.Role;
import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.dto.request.LoginRequest;
import id.co.mii.serverApp.models.dto.request.UserRequest;
import id.co.mii.serverApp.models.dto.response.LoginResponse;
import id.co.mii.serverApp.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private EmployeeService employeeService;
    private JobService jobService;
    private ModelMapper modelMapper;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private AppUserDetailService appUserDetailService;
    private final VerificationTokenService verificationTokenService;
    private EmailService emailService;

    public User register(UserRequest userRequest) {
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
                emailService.sendHtmlEmail(u,userRequest);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        return saved.get();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        // authentication => login request = username & password
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword());

        // set principle
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = userRepository
                .findByUsernameOrEmployee_Email(
                        loginRequest.getUsername(),
                        loginRequest.getUsername())
                .get();

        if (!user.getIsEnabled()) {
            System.out.println("User is disabled");
            throw new DisabledException("User is disabled");
        }

        UserDetails userDetails = appUserDetailService.loadUserByUsername(
                loginRequest.getUsername());

        List<String> authorities = userDetails
                .getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        // response => user detail = username, email, List<GrantedAuthority>
        return new LoginResponse(
                user.getUsername(),
                user.getEmployee().getEmail(),
                authorities);
    }
}
