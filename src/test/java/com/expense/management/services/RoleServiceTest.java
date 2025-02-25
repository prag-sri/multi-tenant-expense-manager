package com.expense.management.services;

import com.expense.management.controllers.RoleController;
import com.expense.management.exceptions.RoleAlreadyExistsException;
import com.expense.management.exceptions.RoleNotFoundException;
import com.expense.management.models.Role;
import com.expense.management.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role1;
    private Role role2;

    @BeforeEach
    void setup(){
        role1 = new Role(1L, "ROLE_ADMIN");
        role2 = new Role(2L, "ROLE_USER");
    }

    @Test
    void testCreateRole_Success(){
        // Given
        when(roleRepository.existsByName(role2.getName())).thenReturn(false);
        when(roleRepository.save(role2)).thenReturn(role2);

        // When
        roleService.createRole(role2);

        // Then
        verify(roleRepository, times(1)).save(role2);
    }

    @Test
    void testCreateRole_Failure_IllegalArgument(){
        // When
        assertThrows(IllegalArgumentException.class, () -> roleService.createRole(role1));

        // Then
        verify(roleRepository, never()).save(role1);
    }

    @Test
    void testCreateRole_Failure_RoleAlreadyExists(){
        // Given
        when(roleRepository.existsByName(role2.getName())).thenReturn(true);

        // When
        assertThrows(RoleAlreadyExistsException.class, () -> roleService.createRole(role2));

        // Then
        verify(roleRepository, never()).save(role2);
    }

    @Test
    void testGetAllRoles(){
        // Given
        List<Role> roles = Arrays.asList(role1, role2);
        when(roleRepository.findAll()).thenReturn(roles);

        // When
        List<Role> response = roleService.getAllRoles();

        // Then
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("ROLE_ADMIN", response.get(0).getName());

        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testGetRole_Success(){
        // Given
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role2));

        // When
        Role response = roleService.getRole(2L);

        // Then
        assertEquals("ROLE_USER", response.getName());
        verify(roleRepository, times(1)).findById(2L);
    }

    @Test
    void testGetRole_Failure_RoleNotFound(){
        // Given
        when(roleRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        assertThrows(RoleNotFoundException.class, () -> roleService.getRole(2L));

        // Then
        verify(roleRepository, times(1)).findById(2L);
    }

    @Test
    void testUpdateRole_Success(){
        // Given
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role2));
        when(roleRepository.existsByName(role2.getName())).thenReturn(false);
        when(roleRepository.save(role2)).thenReturn(role2);

        // When
        roleService.updateRole(2L, role2);

        // Then
        verify(roleRepository, times(1)).save(role2);
    }

    @Test
    void testUpdateRole_Failure_RoleNotFound(){
        // Given
        when(roleRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        assertThrows(RoleNotFoundException.class, () -> roleService.updateRole(2L, role2));

        // Then
        verify(roleRepository, never()).save(role2);
    }

    @Test
    void testUpdateRole_Failure_IllegalArgument(){
        // Given
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role1));

        // When
        assertThrows(IllegalArgumentException.class, () -> roleService.updateRole(1L, role1));

        // Then
        verify(roleRepository, never()).save(role1);
    }

    @Test
    void testUpdateRole_Failure_RoleAlreadyExists(){
        // Given
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role2));
        when(roleRepository.existsByName(role2.getName())).thenReturn(true);

        // When
        assertThrows(RoleAlreadyExistsException.class, () -> roleService.updateRole(2L, role2));

        // Then
        verify(roleRepository, never()).save(role2);
    }

    @Test
    void testDeleteRole_Success(){
        // Given
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role2));
        doNothing().when(roleRepository).deleteById(2L);

        // When
        roleService.deleteRole(2L);

        // Then
        verify(roleRepository,times(1)).deleteById(2L);
    }

    @Test
    void testDeleteRole_Failure_RoleNotFound(){
        // Given
        when(roleRepository.findById(3L)).thenReturn(Optional.empty());

        // When
        assertThrows(RoleNotFoundException.class, () -> roleService.deleteRole(3L));

        // Then
        verify(roleRepository,never()).deleteById(3L);
    }

    @Test
    void testDeleteRole_Failure_IllegalArgument(){
        // Given
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role1));

        // When
        assertThrows(IllegalArgumentException.class, () -> roleService.deleteRole(1L));

        // Then
        verify(roleRepository,never()).deleteById(1L);
    }
}
