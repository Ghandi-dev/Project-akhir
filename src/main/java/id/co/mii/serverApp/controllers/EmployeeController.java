package id.co.mii.serverApp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.usertype.UserVersionType;
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
import id.co.mii.serverApp.models.Role;
import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.dto.request.EmployeeRequest;
import id.co.mii.serverApp.services.EmployeeService;
import id.co.mii.serverApp.services.RoleService;
import id.co.mii.serverApp.services.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;
    private UserService userService;
    private RoleService roleService;

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/all-manager")
    public List<Employee> getManager() {
        return employeeService.getManager();
    }

    @GetMapping("/manager")
    public List<Employee> getByManagerId() {
        return employeeService.getByManagerId();
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
    public Employee update(
            @PathVariable Integer id,
            @RequestBody Employee employee) {
        User user = userService.getById(id);
        List<Role> roles = new ArrayList<>();
        if (employee.getJob().getId() == 2) {
            roles.add(roleService.getById(2));
        } else {
            roles.add(roleService.getById(3));
        }
        user.setRoles(roles);
        userService.update(id, user);
        return employeeService.update(id, employee);
    }
}
