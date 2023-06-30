package id.co.mii.serverApp.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.dto.request.DataCountRequest;
import id.co.mii.serverApp.repositories.EmployeeRepository;
import id.co.mii.serverApp.repositories.OvertimeRepository;
import id.co.mii.serverApp.repositories.ProjectRepository;
import id.co.mii.serverApp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DataCountService {
    private EmployeeRepository employeeRepository;
    private OvertimeRepository overtimeRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    public DataCountRequest dataCount() {

        DataCountRequest dataCountRequest = new DataCountRequest();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        dataCountRequest.setCountAllEmployee(employeeRepository.countEmployee());
        dataCountRequest.setCountAllOvertime(overtimeRepository.countAll());
        dataCountRequest.setCountAllProject(projectRepository.countAll());
        dataCountRequest.setCountOvertimeByEmployee(overtimeRepository.countByEmployee(user.getId()));
        dataCountRequest.setCountOvertimeByManager(overtimeRepository.countByManager(user.getId()));
        dataCountRequest.setCountProjectByEmployee(projectRepository.countByEmployee(user.getId()));
        dataCountRequest.setCountProjectByManager(projectRepository.countByManager(user.getId()));
        return dataCountRequest;
    }

}
