package id.co.mii.serverApp.services;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.dto.request.EmployeeRequest;
import id.co.mii.serverApp.repositories.EmployeeRepository;
import id.co.mii.serverApp.repositories.UserRepository;

import java.util.List;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private JobService jobService;
    private ModelMapper modelMapper;
    private UserRepository userRepository;

    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    public List<Employee> getByManagerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return employeeRepository.getByManagerId(user.getId());
    }

    public List<Employee> getByJobId(Integer id) {
        return employeeRepository.getByJobId(id);
    }

    public List<Employee> getManager() {
        return employeeRepository.getManager();
    }

    public Employee getById(Integer id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found!!!"));
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee createWithDto(EmployeeRequest employeeRequest) {
        Employee employee = modelMapper.map(employeeRequest, Employee.class);
        employee.setManager(getById(employeeRequest.getManagerId()));
        employee.setJob(jobService.getById(employeeRequest.getJobId()));
        return employeeRepository.save(employee);
    }

    public Employee update(Integer id, Employee employee) {
        getById(id);
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    public Employee updateWithDto(Integer id, EmployeeRequest employeeRequest) {
        getById(id);
        Employee employee = modelMapper.map(employeeRequest, Employee.class);
        employee.setManager(getById(employeeRequest.getManagerId()));
        employee.setJob(jobService.getById(employeeRequest.getJobId()));
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    public Employee delete(Integer id) {
        Employee employee = getById(id);
        employeeRepository.delete(employee);
        return employee;
    }
}
