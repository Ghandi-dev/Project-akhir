package id.co.mii.serverApp.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.co.mii.serverApp.models.History;
import id.co.mii.serverApp.models.Overtime;
import id.co.mii.serverApp.models.OvertimeStatus;
import id.co.mii.serverApp.repositories.OvertimeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OvertimeService {

    private OvertimeRepository overtimeRepository;
    private EmailService emailService;
    private EmployeeService employeeService;
    private ProjectService projectService;
    private HistoryService historyService;

    public List<Overtime> getAll() {
        return overtimeRepository.findAll();
    }

    public Overtime getById(Integer id) {
        return overtimeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Overtime not found!!!"));
    }

    public Overtime create(Overtime overtime) {
        overtime.setEmployee(employeeService.getById(overtime.getEmployee().getId()));
        overtimeRepository.save(overtime);
        History history = new History();
        history.setOvertime(overtime);
        history.setStatus(overtime.getStatus());
        historyService.create(history);
        try {
            emailService.sendRequestNotificationByEmail(overtime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return overtime;
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

    public Overtime approveOvertime(Integer id, Overtime overtime, String role) {
        History history = new History();
        overtime.setEmployee(employeeService.getById(overtime.getEmployee().getId()));
        if (role.equalsIgnoreCase("manager")) {
            overtime.setStatus(OvertimeStatus.APPROVED_MANAGER);
            try {
                emailService.sendRequestNotificationByEmail(overtime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            overtime.setStatus(OvertimeStatus.APPROVED_HR);
            projectService.updateBudget(id,
                    overtime.getOvertimePay());
            try {
                emailService.sendApprovedNotification(overtime, role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        update(id, overtime);
        history.setOvertime(overtime);
        history.setStatus(overtime.getStatus());
        historyService.create(history);
        return overtime;
    }
}
