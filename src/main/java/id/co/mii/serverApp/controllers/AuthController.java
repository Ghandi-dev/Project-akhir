package id.co.mii.serverApp.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.dto.request.LoginRequest;
import id.co.mii.serverApp.models.dto.request.UserRequest;
import id.co.mii.serverApp.models.dto.response.LoginResponse;
import id.co.mii.serverApp.services.AuthService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping
public class AuthController {

  private AuthService authService;

  @PostMapping("/register")
  public User registrasi(@RequestBody UserRequest userRequest) {
    return authService.register(userRequest);
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    return authService.login(loginRequest);
  }
}
