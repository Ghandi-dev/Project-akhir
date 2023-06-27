package id.co.mii.serverApp.controllers;

import java.io.IOException;
import java.sql.Timestamp;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.VerificationToken;
import id.co.mii.serverApp.models.dto.request.LoginRequest;
import id.co.mii.serverApp.models.dto.request.UserRequest;
import id.co.mii.serverApp.models.dto.response.LoginResponse;
import id.co.mii.serverApp.services.AuthService;
import id.co.mii.serverApp.services.StorageService;
import id.co.mii.serverApp.services.UserService;
import id.co.mii.serverApp.services.VerificationTokenService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping
public class AuthController {

    private AuthService authService;
    private UserService userService;
    private StorageService storageService;
    private VerificationTokenService verificationTokenService;

    @PostMapping("/register")
    public User registrasi(@RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("managerId") Integer managerId,
            @RequestParam("jobId") Integer jobId,
            @RequestParam("file") MultipartFile file)
            throws IOException {
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setEmail(email);
        userRequest.setPhone(phone);
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setManagerId(managerId);
        userRequest.setJobId(jobId);
        String image = storageService.uploadImageToFileSystem(file);
        userRequest.setPhoto(image);
        return authService.register(userRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/activation")
    public String activation(@RequestParam("token") String token, Model model) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", "Your token is invalid");
        } else {
            User user = verificationToken.getUser();
            // if the user account is not actived
            if (!user.getIsEnabled()) {
                // get current timestamp
                Timestamp curenTimestamp = new Timestamp(System.currentTimeMillis());
                // check if the token is expired
                if (verificationToken.getExpiryDate().before(curenTimestamp)) {
                    model.addAttribute("message", "Your verification token has expired");
                } else {
                    model.addAttribute("message", "Congrats");
                    user.setIsEnabled(true);
                    userService.update(user.getId(), user);
                }
            } else {
                model.addAttribute("message", "your account is already activated");
            }
        }
        return "activation";
    }
}
