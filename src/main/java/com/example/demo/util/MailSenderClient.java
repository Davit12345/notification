package com.example.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailSenderClient {


    @Autowired
    private MailSender mailSender;

    public void send(String to,String tittle,String content){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(tittle);
        mailMessage.setText(content);
        mailSender.send();
    }
}
