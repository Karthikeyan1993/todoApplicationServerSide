package com.app.todo.service;

import com.app.todo.entity.Todo;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {
    void sendForgotPasswordMail(String email,String token) throws MessagingException;
    void sendActivationMail(String email,String token) throws MessagingException;
    void sendOverdueMail(String email, List<Todo> todoLists) throws MessagingException;
}
