package id.co.mii.serverApp.models.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class ProjectRequest {

    private String name;
    private String client;
    private String description;
    private String date_start;
    private String date_end;
    private Integer budget;
    private Integer managerId;
    private List<Integer> employeesId;

}
