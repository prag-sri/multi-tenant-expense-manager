package com.expense.management.services;

import com.expense.management.exceptions.RoleAlreadyExistsException;
import com.expense.management.exceptions.RoleNotFoundException;
import com.expense.management.models.Role;
import com.expense.management.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository= roleRepository;
    }

    // Get all roles
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    // Get role by id
    public Role getRole(Long id){
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: "+id));
    }

    // Create a new role (Admin only, cannot create ADMIN, no duplicates)
    @Transactional
    public void createRole(Role role){
        if ("ROLE_ADMIN".equals(role.getName())) {
            throw new IllegalArgumentException("Cannot create ADMIN");
        }

        // existsByName() is more efficient than findByName() because it avoids fetching the entire entity from the database
        if (roleRepository.existsByName(role.getName())) {
            throw new RoleAlreadyExistsException("Role already exists");
        }
        roleRepository.save(role);
    }

    // Update role (Admin only, cannot update ADMIN, no duplicates)
    @Transactional
    public void updateRole(Long id, Role newRoleData){
        Role role= roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        if(role.getName().equalsIgnoreCase("ROLE_ADMIN")){
            throw new IllegalArgumentException("Cannot update ADMIN");
        }

        // existsByName() is more efficient than findByName() because it avoids fetching the entire entity from the database
        if(roleRepository.existsByName(newRoleData.getName())){
            throw new RoleAlreadyExistsException("Role already exists");
        }

        role.setName(newRoleData.getName());
        roleRepository.save(role);
    }

    // Delete role (Admin only, cannot delete ADMIN)
    @Transactional
    public void deleteRole(Long id){
        Role role= roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        if(role.getName().equals("ROLE_ADMIN")){
            throw new IllegalArgumentException("Cannot delete ADMIN");
        }
        roleRepository.deleteById(id);
    }
}