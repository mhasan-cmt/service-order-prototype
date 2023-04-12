package com.example.stu.service;

import com.example.stu.web.dto.UserDto;
import com.example.stu.entity.User;

import java.util.List;

public interface IUserService {
    void saveUser(UserDto userDto);
    void update(User User);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    Boolean existsByEmail(String email);

    User findById(Long id);

    UserDto convertEntityToDto(User user);
}
