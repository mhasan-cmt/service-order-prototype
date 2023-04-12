package com.example.stu.web.dto;

import com.example.stu.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayInPersonRequest {
    private Booking booking;
    private String name, email;
}
