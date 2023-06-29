package id.co.mii.serverApp.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.co.mii.serverApp.models.History;
import id.co.mii.serverApp.models.Overtime;
import id.co.mii.serverApp.models.OvertimeStatus;
import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.repositories.OvertimeRepository;
import id.co.mii.serverApp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OvertimeService {

    private OvertimeRepository overtimeRepository;
    private EmailService emailService;
    private EmployeeService employeeService;
    private ProjectService projectService;
    private HistoryService historyService;
    private UserRepository userRepository;

    public List<Overtime> getAll() {
        return overtimeRepository.findAll();
    }

    public List<Overtime> getAllForHr() {
        return overtimeRepository.getAllForHr();
    }

    public Overtime getById(Integer id) {
        return overtimeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Overtime not found!!!"));
    }

    public List<Overtime> getByEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return overtimeRepository.getByEmployeeId(user.getId());
    }

    public List<Overtime> getByManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return overtimeRepository.getByManagerId(user.getId());
    }

    public Overtime create(Overtime overtime) {
        overtime.setEmployee(employeeService.getById(overtime.getEmployee().getId()));
        overtime.setStatus(OvertimeStatus.PROCESS);
        Overtime response = overtimeRepository.save(overtime);
        History history = new History();
        history.setOvertime(response);
        history.setStatus(response.getStatus());
        historyService.create(history);
        try {
            emailService.sendRequestNotificationByEmail(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public Overtime update(Integer id, Overtime overtime) {
        overtime.setId(id);
        return overtimeRepository.save(overtime);
    }

    public Overtime delete(Integer id) {
        Overtime overtime = getById(id);
        overtimeRepository.delete(overtime);
        return overtime;
    }

    public Overtime approveOvertime(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        History history = new History();
        Overtime overtime = getById(id);
        String role = user.getRoles().get(0).getName();
        if (role.equalsIgnoreCase("manager")) {
            overtime.setStatus(OvertimeStatus.APPROVED_MANAGER);
            try {
                emailService.sendRequestNotificationByEmail(overtime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            overtime.setStatus(OvertimeStatus.APPROVED_HR);
            projectService.updateBudget(overtime.getProject().getId(),
                    overtime.getOvertimePay());
            try {
                emailService.sendApprovedNotification(overtime, role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Overtime response = update(id, overtime);
        history.setOvertime(response);
        history.setStatus(response.getStatus());
        historyService.create(history);
        return response;
    }

    public Overtime rejectOvertime(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        String role = user.getRoles().get(0).getName();
        History history = new History();
        Overtime overtime = getById(id);
        overtime.setStatus(OvertimeStatus.REJECTED);
        try {
            emailService.sendRejectedNotification(overtime, role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Overtime response = update(id, overtime);
        history.setOvertime(response);
        history.setStatus(response.getStatus());
        historyService.create(history);
        return response;
    }
}
