package id.co.mii.serverApp.models.dto.request;

import lombok.Data;

@Data
public class UserRequest {

  private String name;
  private String email;
  private String phone;
  private String photo;
  private String username;
  private String password;
  private Integer managerId;
  private Integer jobId;

}
