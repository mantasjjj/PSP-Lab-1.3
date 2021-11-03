package com.psp.service;

import com.psp.model.User;
import com.psp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    public User findById(int userId) {
        return repository.findById(userId).get();
    }

    public void updateUser(User userInfo) {
        repository.save(userInfo);
    }

    public User addUser(User user) {
        return repository.save(user);
    }

    public void deleteUserById(int id) {
        repository.deleteById(id);
    }

    public boolean doesUserExist(int userId) {
        return repository.existsById(userId);
    }
}
