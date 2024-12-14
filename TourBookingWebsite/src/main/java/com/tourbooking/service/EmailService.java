package com.tourbooking.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;  // SpringTemplateEngine để xử lý Thymeleaf template

    public void sendForgotPasswordEmail(String to, String resetLink) throws MessagingException {
        // Tạo context để chứa các biến động
        Context context = new Context();
        context.setVariable("resetLink", resetLink);
        // Xử lý email template bằng Thymeleaf
        String emailContent = templateEngine.process("forgot-password-email", context);

        // Tạo email với nội dung đã được xử lý
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Reset Your Password");
        helper.setText(emailContent, true); // true để gửi HTML email
        System.out.println(emailContent);

        mailSender.send(message);
    }
}
