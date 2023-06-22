package id.co.mii.serverApp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponse {

  private Integer id;
  private String name;
  private String job;
}
