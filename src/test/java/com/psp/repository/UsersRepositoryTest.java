package com.psp.repository;

import com.psp.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UsersRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void testSave() {
        User user = new User(1, "Tadas", "Tadiukinas", "+37062539592", "tadas@gmail.com", "Adreso g. 5", "Password2!");
        repository.save(user);

        User user1 = repository.findById(1).isPresent() ? repository.findById(1).get() : null;

        assertNotNull(user1);
        assertEquals("Tadas", user1.getFirstName());
    }

    @Test
    public void testFindAll() {
        User user = new User(1, "Tadas", "Tadiukinas", "+37062539592", "tadas@gmail.com", "Adreso g. 5", "Password2!");
        repository.save(user);

        Iterable<User> users = repository.findAll();

        assertNotNull(users);

        List<User> result = new ArrayList<>();
        users.forEach(result::add);

        assertEquals(1, result.size());
    }

    @Test
    public void testDelete() {
        User user = new User(1, "Tadas", "Tadiukinas", "+37062539592", "tadas@gmail.com", "Adreso g. 5", "Password2!");
        repository.save(user);

        User u = repository.findById(1).isPresent() ? repository.findById(1).get() : null;
        assertNotNull(u);

        repository.delete(u);

        Iterable<User> users = repository.findAll();

        List<User> result = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());

        assertEquals(0, result.size());
    }
}
