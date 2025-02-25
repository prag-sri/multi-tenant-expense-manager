package com.expense.management.controllers;

import com.expense.management.models.Role;
import com.expense.management.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private Role role1;
    private Role role2;

    @BeforeEach
    void setup(){
        role1 = new Role(1L, "Role1");
        role2 = new Role(2L, "Role2");
    }

    @Test
    void testCreateRole(){
        // Given
        doNothing().when(roleService).createRole(any(Role.class));

        // When
        ResponseEntity<String> response = roleController.createRole(role1);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertEquals("Role created successfully!", response.getBody());

        verify(roleService, times(1)).createRole(role1);
    }

    @Test
    void testGetAllRoles(){
        // Given
        List<Role> roles = Arrays.asList(role1, role2);
        when(roleService.getAllRoles()).thenReturn(roles);

        // When
        ResponseEntity<List<Role>> response = roleController.getAllRoles();

        // Then
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Role1", response.getBody().get(0).getName());

        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    void testGetRole(){
        // Given
        when(roleService.getRole(1L)).thenReturn(role1);

        // When
        ResponseEntity<Role> response = roleController.getRole(1L);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Role1", response.getBody().getName());

        verify(roleService, times(1)).getRole(1L);
    }

    @Test
    void testUpdateRole(){
        // Given
        doNothing().when(roleService).updateRole(1L, role1);

        // When
        ResponseEntity<String> response = roleController.updateRole(1L, role1);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Role updated successfully", response.getBody());

        verify(roleService, times(1)).updateRole(1L, role1);
    }

    @Test
    void testDeleteRole(){
        // Given
        doNothing().when(roleService).deleteRole(1L);

        // When
        ResponseEntity<String> response = roleController.deleteRole(1L);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Role deleted successfully", response.getBody());

        verify(roleService, times(1)).deleteRole(1L);
    }
}
