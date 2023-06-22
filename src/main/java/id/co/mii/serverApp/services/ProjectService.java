package id.co.mii.serverApp.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.co.mii.serverApp.models.Project;
import id.co.mii.serverApp.models.dto.response.ProjectResponse;
import id.co.mii.serverApp.repositories.ProjectRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
    
    private ProjectRepository projectRepository;
    private ModelMapper modelMapper;

    public List<Project> getAll() {
        return projectRepository.findAll();
    }
    
    // public List<ProjectResponse> getAllWithDto() {
    //     List<ProjectResponse> projectResponse = new ArrayList<>();
    //     projectResponse.add(modelMapper.map(projectRepository.findAll(), ProjectResponse.class));
    //     return projectResponse;
    // }

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

    public Project delete(Integer id) {
        Project project = getById(id);
        projectRepository.delete(project);
        return project;
    }
}
