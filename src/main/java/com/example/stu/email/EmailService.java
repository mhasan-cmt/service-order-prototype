package com.example.stu.email;

import com.example.stu.entity.Booking;
import com.example.stu.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

public interface EmailService {
    void sendEmail(String recipientEmail, String subject, Map<String, Object> emailVariables, String templateName) throws MessagingException;
    String buildEmailDataAndSend(@ModelAttribute Booking booking, String email, @AuthenticationPrincipal User userDetails, Map<String, Object> emailVariables) throws MessagingException ;
}
