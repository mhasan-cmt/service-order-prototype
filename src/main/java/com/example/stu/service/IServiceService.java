package com.example.stu.service;

import com.example.stu.entity.Booking;
import com.example.stu.entity.Services;

import java.util.List;

public interface IServiceService {
    public List<Services> getAll();

    public Services getById(Long id);

    public Services save(Services service);

    void saveAll(List<Services> servicesArrayList);

    Long count();
}
