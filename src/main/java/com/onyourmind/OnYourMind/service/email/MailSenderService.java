package com.onyourmind.OnYourMind.service.email;

import com.onyourmind.OnYourMind.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendMailForRegistration(final User user) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setFrom("OnYourMind");
        message.setSubject("OnYourMind - Registration");
        message.setText("Go to this address to activate your account http://localhost:8080/account-verification.html?id=" + user.getId());

        mailSender.send(message);
    }
}
