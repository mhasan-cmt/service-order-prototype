package com.example.stu.service.impl;

import com.example.stu.entity.RoleName;
import com.example.stu.entity.ServiceProvider;
import com.example.stu.service.IProviderService;
import com.example.stu.web.dto.UserDto;
import com.example.stu.entity.Role;
import com.example.stu.entity.User;
import com.example.stu.repository.RoleRepository;
import com.example.stu.repository.UserRepository;
import com.example.stu.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IUserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IProviderService providerService;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        Role role = roleRepository.findByName(userDto.getRoleName());
        user.setRoles(List.of(role));
        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = userRepository.saveAndFlush(user);
        if (userDto.getRoleName().equals(RoleName.ROLE_PROVIDER)) {
            ServiceProvider serviceProvider = new ServiceProvider();
            serviceProvider.setUser(user);
            providerService.save(serviceProvider);
        }
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        return userDto;
    }
}
