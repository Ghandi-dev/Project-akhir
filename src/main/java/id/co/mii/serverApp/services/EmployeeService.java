package id.co.mii.serverApp.services;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.dto.request.EmployeeRequest;
import id.co.mii.serverApp.repositories.EmployeeRepository;
import java.util.List;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private JobService jobService;
    private ModelMapper modelMapper;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getByManagerId(Integer id) {
        return employeeRepository.getByManagerId(id);
    }
    
    public List<Employee> getByJobId(Integer id) {
        return employeeRepository.getByJobId(id);
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

    public Employee delete(Integer id) {
        Employee employee = getById(id);
        employeeRepository.delete(employee);
        return employee;
    }
}
