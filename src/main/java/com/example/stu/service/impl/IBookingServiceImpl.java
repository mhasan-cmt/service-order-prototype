package com.example.stu.service.impl;

import com.example.stu.entity.Booking;
import com.example.stu.repository.BookingRepository;
import com.example.stu.service.IBookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class IBookingServiceImpl implements IBookingService {
    private final BookingRepository bookingRepository;
    @Override
    public Booking save(Booking booking) {
        booking.setCreatedAt(LocalDateTime.now());
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
