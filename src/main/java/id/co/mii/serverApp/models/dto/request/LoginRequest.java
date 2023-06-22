package id.co.mii.serverApp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

  private String username;
  private String password;
}

