package com.example.sbaynewsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(String to,String subject,String body) {
        MimeMessage messageHelper = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(messageHelper, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Đặt tham số thứ hai thành true để sử dụng định dạng HTML
            javaMailSender.send(messageHelper);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
