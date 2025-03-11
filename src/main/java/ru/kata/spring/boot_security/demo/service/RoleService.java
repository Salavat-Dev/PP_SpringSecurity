package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {
    List<Role> getAllRoles();

    Role saveRole(Role role);
}
