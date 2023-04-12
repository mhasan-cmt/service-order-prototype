package com.example.stu.web.controller;

import com.example.stu.email.EmailService;
import com.example.stu.entity.Booking;
import com.example.stu.entity.PaymentMethod;
import com.example.stu.entity.Services;
import com.example.stu.entity.User;
import com.example.stu.service.IBookingService;
import com.example.stu.service.IServiceService;
import com.example.stu.web.dto.PayInPersonRequest;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {
    private final IServiceService serviceService;
    private final IBookingService bookingService;
    private final EmailService emailService;
    private final Map<String, Object> emailVariables;

    @GetMapping("{id}")
    public String bookServiceForm(@PathVariable Long id, Model model,
                                  @AuthenticationPrincipal User userDetails,
                                  @RequestParam("payment") String paymentMethod) {
        Services service = serviceService.getById(id);
        Booking booking = new Booking();
        booking.setServices(service);
        if (paymentMethod.equals("online")) {
            booking.setPaymentMethod(PaymentMethod.ONLINE);
            model.addAttribute("booking", booking);
            return "/booking/serviceBooking";
        } else if (paymentMethod.equals("person")) {
            booking.setPaymentMethod(PaymentMethod.IN_PERSON);
            PayInPersonRequest payInPersonRequest = new PayInPersonRequest(booking, userDetails.getFullName(), userDetails.getEmail());
            model.addAttribute("payInPersonRequest", payInPersonRequest);
            return "/booking/serviceBookingPerson";
        } else {
            return "redirect:/booking?error=Invalid payment method";
        }

    }

    @PostMapping("/pay-online")
    public String bookService(Model model, @ModelAttribute Booking booking, @AuthenticationPrincipal User userDetails) throws MessagingException {
        Services services = serviceService.getById(booking.getServices().getId());
        booking.setServices(services);
        booking.setUser(userDetails);
        bookingService.save(booking);
        model.addAttribute("booking", booking);
        return emailService.buildEmailDataAndSend(booking, userDetails.getEmail(), userDetails, emailVariables);
    }

    @PostMapping("/pay-person")
    public String bookServiceInPerson(@ModelAttribute PayInPersonRequest payInPersonRequest,Model model, @AuthenticationPrincipal User userDetails) throws MessagingException {
        Services services = serviceService.getById(payInPersonRequest.getBooking().getServices().getId());
        payInPersonRequest.getBooking().setServices(services);
        payInPersonRequest.getBooking().setPaymentMethod(PaymentMethod.IN_PERSON);
        payInPersonRequest.getBooking().setUser(userDetails);
        Booking booking = bookingService.save(payInPersonRequest.getBooking());
        model.addAttribute("booking", payInPersonRequest.getBooking());;
        return emailService.buildEmailDataAndSend(booking, payInPersonRequest.getEmail(), userDetails, emailVariables);
    }
}
