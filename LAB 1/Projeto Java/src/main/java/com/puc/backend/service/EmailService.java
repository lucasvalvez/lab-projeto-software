package com.puc.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.puc.backend.model.Email;

@Service
public class EmailService {
    private final  JavaMailSender mailSender;


    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(Email mail) {
        var msg = new SimpleMailMessage();
        msg.setFrom("rodriguesguilerme4@gmail.com");
        msg.setTo(mail.to());
        msg.setSubject(mail.subject());
        msg.setText("Olá, aluno " + mail.name() + "!\n\n" + "Seja bem-vindo ao sistema de matrículas da PUC Minas. Sua matrícula foi realizada com sucesso.\n\n" + "Atenciosamente,\n" + "Equipe de matrículas da PUC Minas");
        mailSender.send(msg);

        
    }
}