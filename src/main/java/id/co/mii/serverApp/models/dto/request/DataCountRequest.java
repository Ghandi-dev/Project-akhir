package id.co.mii.serverApp.models.dto.request;

import lombok.Data;

@Data
public class DataCountRequest {
    private Integer countProjectByEmployee;
    private Integer countProjectByManager;
    private Integer countAllProject;
    private Integer countOvertimeByEmployee;
    private Integer countOvertimeByManager;
    private Integer countAllOvertime;
    private Integer countAllEmployee;
}
