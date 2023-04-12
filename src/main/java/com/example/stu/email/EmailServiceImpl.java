package com.example.stu.email;

import com.example.stu.entity.Booking;
import com.example.stu.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);


    @Async
    @Override
    public void sendEmail(String recipientEmail, String subject, Map<String, Object> emailVariables, String templateName) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("service-to-you@stu.com");
        helper.setTo(recipientEmail);
        helper.setSubject(subject);

        String content = templateEngine.process(templateName, new Context(Locale.getDefault(), emailVariables));
        helper.setText(content, true);
        LOGGER.info("Sending email to: " + recipientEmail);
        emailSender.send(message);
        LOGGER.info("Email Sent To: " + recipientEmail);
    }

    @Override
    public String buildEmailDataAndSend(Booking booking, String email, User userDetails, Map<String, Object> emailVariables) throws MessagingException {

        emailVariables.put("title", "Booking Confirmation");
        emailVariables.put("service_name", booking.getServices().getName());
        emailVariables.put("created_at", booking.getCreatedAt());
        emailVariables.put("address", booking.getAddress());
        emailVariables.put("payment_method", booking.getPaymentMethod());
        sendEmail(email, "Booking Confirmation", emailVariables, "email-template");
        return "booking/successfulBooking";
    }
}
