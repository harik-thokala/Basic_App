package com.example.workingApp.controller;

import com.example.workingApp.dao.UserDao;
import com.example.workingApp.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Tells Spring this class handles web requests
@RequestMapping("/users") // All URLs in this class start with /users
public class UserController {

    private final UserDao userDao;

    // Spring injects the UserDao we created earlier
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    // GET http://localhost:8080/users
    @GetMapping
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    // POST http://localhost:8080/users
    @PostMapping
    public User createUser(@RequestBody User user) {
        userDao.insert(user);
        return user;
    }
}