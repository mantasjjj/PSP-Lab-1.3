package com.psp.service;

import com.psp.model.User;
import com.psp.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;


    @DisplayName("Test Find All")
    @Test
    void testFindAll() {
        User user = new User(1, "Tadas", "Tadiukinas", "862539592", "tadas@gmail.com", "Adreso g. 5", "Password2!");
        List<User> users = Collections.singletonList(user);

        when(repository.findAll()).thenReturn(users);

        List<User> found = service.findAll();

        verify(repository).findAll();

        assertEquals(1, found.size());
    }

    @Test
    void testFindById() {
        User user = new User(1, "Tadas", "Tadiukinas", "862539592", "tadas@gmail.com", "Adreso g. 5", "Password2!");
        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));

        User found = service.findById(1);
        verify(repository).findById(Mockito.anyInt());
        assertNotNull(found);
    }

    @Test
    void testUpdate() {
        User user = new User(1, "Tadas", "Tadiukinas", "862539592", "tadas@gmail.com", "Adreso g. 5", "Password2!");
        service.updateUser(user);
        verify(repository).save(Mockito.any(User.class));
    }

    @Test
    void testAdd() {
        User user = new User(1, "Tadas", "Tadiukinas", "862539592", "tadas@gmail.com", "Adreso g. 5", "Password2!");
        when(repository.save(Mockito.any(User.class))).thenReturn(user);

        User added = service.addUser(user);
        verify(repository).save(Mockito.any(User.class));
        assertNotNull(added);
    }

    @Test
    void testDeleteById() {
        service.deleteUserById(1);
        verify(repository).deleteById(Mockito.anyInt());
    }

}
