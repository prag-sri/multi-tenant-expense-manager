package com.expense.management.services;

import com.expense.management.exceptions.CompanyNotFoundException;
import com.expense.management.exceptions.InvalidCredentialsException;
import com.expense.management.exceptions.RoleNotFoundException;
import com.expense.management.exceptions.UserAlreadyExistsException;
import com.expense.management.models.Company;
import com.expense.management.models.Role;
import com.expense.management.models.User;
import com.expense.management.repositories.CompanyRepository;
import com.expense.management.repositories.RoleRepository;
import com.expense.management.repositories.UserRepository;
import com.expense.management.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, CompanyRepository companyRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder){
        this.userRepository= userRepository;
        this.roleRepository= roleRepository;
        this.companyRepository= companyRepository;
        this.jwtUtil= jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String registerUser(String username, String password, List<String> roleNames, String companyName){
        Optional<Company> company = companyRepository.findByName(companyName);
        if(company.isEmpty()){
            throw new CompanyNotFoundException("Company not found!");
        }

        if(userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        User user= new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setCompany(company.get());

        Set<Role> roles= roleNames.stream()
                        .map(roleName -> roleRepository.findByName(roleName)
                                .orElseThrow(() -> new RoleNotFoundException("Role not found: "+roleName)))
                                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);
        return jwtUtil.generateToken(username);
    }

    public String authenticateUser(String username, String password){
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials!");
        }
        return jwtUtil.generateToken(username);
    }
}
