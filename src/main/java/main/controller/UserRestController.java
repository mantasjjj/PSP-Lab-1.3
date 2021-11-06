package main.controller;

import main.Validator;
import main.exceptions.ValidationException;
import main.model.User;
import main.rules.PhoneNumberRule;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    UserService service;

    String allowedSymbols = "!@#$%^&*()";
    Validator validator = new Validator(allowedSymbols.toCharArray(), 5, Collections.singletonList(new PhoneNumberRule("LT", 12, "+370")));

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable int userId) {
        return service.findById(userId);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user, @RequestParam String country) throws ValidationException {
        if (user != null) {
            validateUserInfo(user, country);

            return service.addUser(user);
        }
        throw new ValidationException("User info cannot be empty!");
    }

    @PostMapping("/users/{userId}")
    public User updateUserById(@PathVariable int userId, @RequestBody User userInfo, @RequestParam String country) throws ValidationException {
        if (service.doesUserExist(userId)) {
            userInfo.setId(userId);

            validateUserInfo(userInfo, country);
            service.updateUser(userInfo);

            return userInfo;
        }
        throw new ValidationException("User does not exist.");
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserById(@PathVariable int userId) {
        service.deleteUserById(userId);
    }

    private void validateUserInfo(User user, String country) throws ValidationException {
        if (!validator.isEmailValid(user.getEmail())) {
            throw new ValidationException("Invalid email.");
        }
        if (!validator.isPasswordValid(user.getPassword())) {
            throw new ValidationException("Invalid password.");
        }
        if (!validator.isPhoneNumberValid(country, user.getPhoneNumber())) {
            throw new ValidationException("Invalid phoneNumber");
        }
    }
}
