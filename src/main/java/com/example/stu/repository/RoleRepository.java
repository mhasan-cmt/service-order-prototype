package com.example.stu.repository;

import com.example.stu.entity.Role;
import com.example.stu.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
