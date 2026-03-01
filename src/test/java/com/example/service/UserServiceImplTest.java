package com.example.service;

import com.example.dto.UserRequest;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void create_shouldReturnUserResponse() {
        UserRequest request = new UserRequest();
        request.setName("Anna");
        request.setEmail("anna@test.com");
        request.setAge(25);

        User savedUser = new User("Anna", "anna@test.com", 25);
        savedUser.setName("Anna");

        when(repository.save(any(User.class))).thenReturn(savedUser);

        var response = service.create(request);

        assertEquals("Anna", response.getName());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void getById_shouldReturnUser() {
        User user = new User("John", "john@test.com", 30);

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        var response = service.getById(1L);

        assertEquals("John", response.getName());
        verify(repository).findById(1L);
    }

    @Test
    void delete_shouldCallRepository() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }
}