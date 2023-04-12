package com.example.stu.web.controller;

import com.example.stu.entity.Booking;
import com.example.stu.entity.ServiceProvider;
import com.example.stu.entity.Services;
import com.example.stu.entity.User;
import com.example.stu.service.IBookingService;
import com.example.stu.service.IProviderService;
import com.example.stu.service.IServiceService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {
    private final IServiceService serviceService;
    private final IProviderService providerService;
    private final IBookingService bookingService;

    @GetMapping("/prev")
    public String prevServiceForm(Model model, @AuthenticationPrincipal User userDetails) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userDetails.getId());
        model.addAttribute("bookings", bookings);
        return "/service/prevService";
    }

    @GetMapping("/new")
    public String getAll(Model model) {
        List<Services> services = serviceService.getAll();
        model.addAttribute("services", services);
        return "/service/newService";
    }

    @GetMapping("/edit")
    public String editServiceFromProvider(Model model, @AuthenticationPrincipal User userDetails) {
        ServiceProvider provider = providerService.findByUserId(userDetails.getId());
        boolean hasService = provider.getService() != null;
        if (!hasService) {
            provider.setService(new Services());
            model.addAttribute("provider", provider);
        }else {
            model.addAttribute("provider", provider);
        }
        model.addAttribute("hasService", hasService);
        return "/service/edit";
    }
    @PostMapping("/edit")
    public String editServiceFromProviderPost(@ModelAttribute("provider") ServiceProvider serviceProvider) {
        ServiceProvider provider = providerService.findById(serviceProvider.getId());
        if (provider.getService()!=null){
            provider.getService().setName(serviceProvider.getService().getName());
            provider.getService().setPrice(serviceProvider.getService().getPrice());
            providerService.save(provider);
        }
        else {
            provider.setService(serviceProvider.getService());
            provider.getService().setServiceProvider(provider);
            providerService.save(provider);
        }
        return "redirect:/service/edit?success&providerId=" + provider.getId();
    }
}
