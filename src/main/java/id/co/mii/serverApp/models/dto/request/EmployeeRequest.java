package id.co.mii.serverApp.models.dto.request;

import lombok.Data;

@Data
public class EmployeeRequest {
    
    private String name;
    private String email;
    private String phone;
    private Integer managerId;
    private Integer jobId;
}
