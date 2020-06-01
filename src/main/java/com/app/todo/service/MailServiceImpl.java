package com.app.todo.service;

import com.app.todo.entity.Todo;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service("mailService")
public class MailServiceImpl implements MailService {

    private static final String UI_BASE_URL_DEV = "http://localhost:4200/#";
    private static final String UI_BASE_URL_PROD = "https://angular-bull-mytodo.herokuapp.com/#";

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendForgotPasswordMail(String email, String token) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("karthik.infotec98@gmail.com");
        helper.setTo(new String[]{email});
        msg.setSubject("Password Reset Link - MyTodo");
        String builder = "<p>You told us you forgot your password. If you really did use below link to reset your password.</p>" +
                "<p>Click here to reset the password <a href=" + this.UI_BASE_URL_DEV+ "reset?token=" + token + ">Click here</a></p>" +
                "<p>If you didn't mean to reset your password, then you can just ignore this email;your password will not change</p>"
        + "<p>Best Regards,</p>" +
                "<p><img src='https://avatars1.githubusercontent.com/u/27134751?s=460&u=bb85057bab39a2bca8208605f2553a5233f792e9&v=4' width='80' height='80'></p>";
        msg.setText(builder);
        javaMailSender.send(msg);
    }

    @Override
    public void sendActivationMail(String email, String token) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("karthik.infotec98@gmail.com");
        helper.setTo(new String[]{email});
        msg.setSubject("Activate Your Account - MyTodo");
        String builder = "<p>Thanks for registering, You're account is almost ready.</p>" +
                "<p>To activate your account please click the below link.</p>" +
                "<p>Activation Link <a href=" + this.UI_BASE_URL_DEV +"activate?token=" + token + ">Click here</a></p>" +
                "<p>Best Regards,</p>" +
                "<p><img src='https://avatars1.githubusercontent.com/u/27134751?s=460&u=bb85057bab39a2bca8208605f2553a5233f792e9&v=4' width='80' height='80'></p>";
        msg.setText(builder);
        javaMailSender.send(msg);

    }

    @Override
    public void sendOverdueMail(String email, List<Todo> todoLists) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("karthik.infotec98@gmail.com");
        helper.setTo(new String[]{email});
        msg.setSubject("MyTodo - Task Over Due Notification");
        StringBuilder builder = new StringBuilder();
        builder.append("<p>Dear User,</p>");
        builder.append("<p>We have identified below overdue task's in your bucket.</p>");
        builder.append("<table border='1' style=' border-collapse: collapse;'>");
        builder.append("<thead>");
        builder.append("<th>".concat("Task Name").concat("</th>"));
        builder.append("<th>".concat("Task Overdue Date").concat("</th>"));
        builder.append("</thead><tbody>");
        for(Todo todo:todoLists){
            builder.append("<tr>");
            builder.append("<td>".concat(todo.getName()).concat("</td>"));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            builder.append("<td>".concat(formatter.format(todo.getDuedate())).concat("</td>"));
            builder.append("</tr>");
        }
        builder.append("</tbody></table>");
        builder.append("<p>Please complete it the task as soon as possible</p>");
        builder.append("<p>Best Regards,</p>");
        builder.append("<p><img src='https://avatars1.githubusercontent.com/u/27134751?s=460&u=bb85057bab39a2bca8208605f2553a5233f792e9&v=4' width='80' height='80'></p>");
        msg.setText(builder.toString());
        javaMailSender.send(msg);
    }
}
