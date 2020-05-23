package com.app.todo.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("mailService")
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendForgotPasswordMail(String email, String token) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("noreply@angular-bull.com");
        helper.setTo(new String[]{email});
        msg.setSubject("Password Reset Link - MyTodo");
        String builder = "<p>You told us you forgot your password. If you really did use below link to reset your password.</p>" +
                "<p>Click here to reset the password <a href=" + "http://localhost:4200/forgot-password?token=" + token + ">Click here</a></p>" +
                "<p>If you didn't mean to reset your password, then you can just ignore this email;your password will not change</p>";
        msg.setText(builder);
        javaMailSender.send(msg);
    }
}
