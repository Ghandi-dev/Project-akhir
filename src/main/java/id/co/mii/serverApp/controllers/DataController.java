package id.co.mii.serverApp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.dto.request.DataCountRequest;
import id.co.mii.serverApp.services.DataCountService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/data")
@AllArgsConstructor
public class DataController {
    private DataCountService dataCountService;

    @GetMapping
    public DataCountRequest getAll() {
        return dataCountService.dataCount();
    }
}
