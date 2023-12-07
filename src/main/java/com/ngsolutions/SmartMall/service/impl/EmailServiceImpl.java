package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String smartMallEmail;

    public EmailServiceImpl(
            TemplateEngine templateEngine,
            JavaMailSender javaMailSender,
            @Value("${mail.smartmall}") String smartMallEmail) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.smartMallEmail = smartMallEmail;
    }

    @Override
    public void sendRegistrationEmail(String userEmail, String userName)  {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {

            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(smartMallEmail);
            mimeMessageHelper.setReplyTo(smartMallEmail);
            mimeMessageHelper.setSubject("Welcome to SmartMall!");
            mimeMessageHelper.setText(generateRegistrationEmailBody(userName), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRegistrationEmailBody(String userName) {

        Context context = new Context();
        context.setVariable("username", userName);

        return templateEngine.process("email/registration-email", context);
    }
}
