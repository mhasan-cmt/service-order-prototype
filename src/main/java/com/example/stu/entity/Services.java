package com.example.stu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price = BigDecimal.ZERO;
    @OneToOne(cascade = CascadeType.ALL)
    private ServiceProvider serviceProvider;
    public Services(String name) {
        this.name = name;
    }
}
