package id.co.mii.serverApp.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.Overtime;
import id.co.mii.serverApp.models.OvertimeStatus;
import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.VerificationToken;
import id.co.mii.serverApp.models.dto.request.UserRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {

    private final VerificationTokenService verificationTokenService;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private EmployeeService employeeService;

    // send email activation account
    public void sendHtmlEmail(User user, UserRequest userRequest) throws MessagingException {
        Employee employee = employeeService.getById(user.getId());
        VerificationToken verificationToken = verificationTokenService.findByUser(user);
        if (verificationToken != null) {
            String token = verificationToken.getToken();
            Context context = new Context();
            context.setVariable("title", "activation user");
            context.setVariable("username", userRequest.getUsername());
            context.setVariable("password", userRequest.getPassword());
            context.setVariable("link", "http://localhost:9000/activation?token=" + token);
            String body = templateEngine.process("verification", context);

            // send verification email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(employee.getEmail());
            helper.setSubject("user verification");
            helper.setText(body, true);
            javaMailSender.send(message);
        }

    }

    // send notification overtime request by email
    public void sendRequestNotificationByEmail(Overtime overtime) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", overtime.getEmployee().getName());
        String body = templateEngine.process("notificationRequest", context);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        if (overtime.getStatus() == OvertimeStatus.APPROVED_MANAGER) {
            helper.setTo(employeeService.getById(1).getEmail());
            sendApprovedNotification(overtime, "manager");
        } else {
            helper.setTo(overtime.getEmployee().getManager().getEmail());
        }
        helper.setSubject("Overtime request");
        helper.setText(body, true);
        javaMailSender.send(message);
    }

    // send notification overtime approved
    public void sendApprovedNotification(Overtime overtime, String role) throws MessagingException {
        Context context = new Context();
        context.setVariable("role", role);
        String body = templateEngine.process("notificationApprove", context);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(overtime.getEmployee().getEmail());
        helper.setSubject("Approve Notification");
        helper.setText(body, true);
        javaMailSender.send(message);
    }

    // send notification project created to employee
    public void sendProjectNotification(List<Employee> employees) throws MessagingException {
        Context context = new Context();
        List<String> receipents = employees.stream().map(e -> e.getEmail()).collect(Collectors.toList());
        context.setVariable("employees", employees);
        String body = templateEngine.process("notificationProject", context);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(receipents.toArray(new String[0]));
        helper.setSubject("New Project Notification");
        helper.setText(body, true);
        javaMailSender.send(message);
    }

}
