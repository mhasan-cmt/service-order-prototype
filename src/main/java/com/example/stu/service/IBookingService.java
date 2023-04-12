package com.example.stu.service;

import com.example.stu.entity.Booking;

import java.util.List;

public interface IBookingService {
    Booking save(Booking booking);
    List<Booking> getBookingsByUserId(Long userId);
}
