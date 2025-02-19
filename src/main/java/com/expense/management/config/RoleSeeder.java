package com.expense.management.config;

import com.expense.management.models.Role;
import com.expense.management.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository){
        this.roleRepository= roleRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        List<String> defaultRoles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        List<Role> rolesToSave= defaultRoles.stream()
                        .filter(roleName -> !roleRepository.existsByName(roleName))
                                .map(roleName -> new Role(null, roleName)) // Ensure `id` is null for new entries
                                        .toList();

       roleRepository.saveAll(rolesToSave);
    }
}
