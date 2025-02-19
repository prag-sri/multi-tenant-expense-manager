package com.expense.management.controllers;

import com.expense.management.models.Role;
import com.expense.management.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService= roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createRole(@Valid @RequestBody Role role){
        roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role created successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id){
        return ResponseEntity.ok(roleService.getRole(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRole(@PathVariable Long id, @Valid @RequestBody Role role){
        roleService.updateRole(id,role);
        return ResponseEntity.ok("Role updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}