package id.co.mii.serverApp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.Project;
import id.co.mii.serverApp.models.dto.request.ProjectRequest;
import id.co.mii.serverApp.repositories.ProjectRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;
    private EmployeeService employeeService;
    private EmailService emailService;
    private ModelMapper modelMapper;

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public List<Project> getByManagerId(Integer id) {
        return projectRepository.GetByManagerId(id);
    }

    public List<Project> getByEmployeeId(Integer id) {
        return projectRepository.GetByEmployeeId(id);
    }

    public Project getById(Integer id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Project not found!!!"));
    }

    public Project create(ProjectRequest projectRequest) {
        Employee manager = employeeService.getById(projectRequest.getManagerId());
        List<Employee> employees = projectRequest.getEmployeesId().stream().map(e -> employeeService.getById(e))
                .collect(Collectors.toList());
        Project project = modelMapper.map(projectRequest, Project.class);
        project.setManager(manager);
        try {
            emailService.sendProjectNotification(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
        project.setEmployees(employees);

        return projectRepository.save(project);
    }

    public Project update(Integer id, Project project) {
        getById(id);
        project.setId(id);
        return projectRepository.save(project);
    }

    public Project updateBudget(Integer id, Integer overtimePay) {
        Project project = getById(id);
        project.setBudget(project.getBudget() - overtimePay);
        return projectRepository.save(project);
    }

    public Project delete(Integer id) {
        Project project = getById(id);
        projectRepository.delete(project);
        return project;
    }

}
