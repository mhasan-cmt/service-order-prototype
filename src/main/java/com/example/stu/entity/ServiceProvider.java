package com.example.stu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="service_provider")
@Data
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Services service;
    @OneToOne
    private User user;
}
