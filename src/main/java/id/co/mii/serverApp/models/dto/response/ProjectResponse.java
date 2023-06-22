package id.co.mii.serverApp.models.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectResponse {
    private Integer id;
    private String name;
    private String client;
    private String description;
    private String date_start;
    private String date_end;
    private Integer budget;
    private EmployeeResponse employee;
    private List<EmployeeResponse> employees;
}
