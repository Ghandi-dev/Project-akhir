package id.co.mii.serverApp.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.dto.request.EmployeeRequest;
import id.co.mii.serverApp.services.EmployeeService;
import id.co.mii.serverApp.services.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;
    private UserService userService;

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/manager")
    public List<Employee> getByManagerId(@RequestParam("managerId") Integer id) {
        return employeeService.getByManagerId(id);
    }

    @GetMapping("/job")
    public List<Employee> getByJobId(@RequestParam("jobId") Integer id) {
        return employeeService.getByJobId(id);
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id) {
        return employeeService.getById(id);
    }

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PostMapping("/dto")
    public Employee createWithDto(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.createWithDto(employeeRequest);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable Integer id) {
        return employeeService.delete(id);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("managerId") Integer managerId,
            @RequestParam("jobId") Integer jobId,
            @RequestParam("file") MultipartFile file)
            throws IOException {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName(name);
        employeeRequest.setEmail(email);
        employeeRequest.setPhone(phone);
        employeeRequest.setManagerId(managerId);
        employeeRequest.setJobId(jobId);
        userService.updatePhoto(id, file);
        return employeeService.updateWithDto(id, employeeRequest);
    }
}
