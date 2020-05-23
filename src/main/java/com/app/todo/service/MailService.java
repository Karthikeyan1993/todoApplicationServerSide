package com.app.todo.service;

import javax.mail.MessagingException;

public interface MailService {
    void sendForgotPasswordMail(String email,String token) throws MessagingException;
}
