package id.co.mii.serverApp.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.co.mii.serverApp.models.Project;
import id.co.mii.serverApp.repositories.ProjectRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
    
    private ProjectRepository projectRepository;

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Project getById(Integer id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Project not found!!!"));
    }

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public Project update(Integer id, Project project) {
        getById(id);
        project.setId(id);
        return projectRepository.save(project);
    }

    public Project updateBudget(Integer id, Integer overtimePay){
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
