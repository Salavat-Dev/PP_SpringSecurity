package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class Initializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Initializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleService.saveRole(role);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userService.getAllUsers().isEmpty()) {
            Role userRole = roleService.getAllRoles().stream()
                    .filter(role -> role.getName().equals("ROLE_USER"))
                    .findFirst()
                    .orElseGet(() -> createRole("ROLE_USER"));

            Role adminRole = roleService.getAllRoles().stream()
                    .filter(role -> role.getName().equals("ROLE_ADMIN"))
                    .findFirst()
                    .orElseGet(() -> createRole("ROLE_ADMIN"));

            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setEmail("user@user.com");
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            user.setRoles(userRoles);
            userService.addUser(user);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@admin.com");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(userRole);
            admin.setRoles(adminRoles);
            userService.addUser(admin);
        }
    }
}
