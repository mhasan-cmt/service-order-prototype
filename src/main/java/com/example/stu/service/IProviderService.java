package com.example.stu.service;

import com.example.stu.entity.ServiceProvider;
import org.springframework.security.core.Authentication;

public interface IProviderService {
    ServiceProvider findByUserId(Long id);

    ServiceProvider findById(Long id);

    ServiceProvider findByUserEmail(String email);

    void save(ServiceProvider serviceProvider);

    Boolean checkIfProviderFromAuthentication(Authentication authentication);
}
