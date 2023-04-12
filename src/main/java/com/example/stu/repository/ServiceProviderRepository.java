package com.example.stu.repository;

import com.example.stu.entity.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    Optional<ServiceProvider> findByUserId(Long id);
    Optional<ServiceProvider> findByUserEmail(String email);
}
