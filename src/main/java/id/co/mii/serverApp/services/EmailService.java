package id.co.mii.serverApp.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.VerificationToken;
import id.co.mii.serverApp.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {
    
    private final VerificationTokenService verificationTokenService;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private EmployeeService employeeService;

    public void sendHtmlEmail(User user) throws MessagingException{
        Employee employee = employeeService.getById(user.getId()); 
        VerificationToken verificationToken = verificationTokenService.findByUser(user);
        if(verificationToken != null){
            String token = verificationToken.getToken();
            Context context = new Context();
            context.setVariable("title", "activation user");
            context.setVariable("link","http://localhost:9000/activation?token="+token);
            String body = templateEngine.process("verification",context);

            // send verification email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper= new MimeMessageHelper(message,true);
            helper.setTo(employee.getEmail());
            helper.setSubject("user verification");
            helper.setText(body,true);
            javaMailSender.send(message);
        }
        
    }
}
