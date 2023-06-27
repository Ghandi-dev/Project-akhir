package id.co.mii.serverApp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataCountResponse {
    private Integer allEmployee; // HR
    private Integer allEmployeeByManagerId; // Manager
    private Integer allOvertime; // HR
    private Integer allOvertimeByManagerId; // Manager
    private Integer allProjectByEmployeeid; // Employee
}
