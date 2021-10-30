package com.pravdin.democassandra.controller;

import com.pravdin.democassandra.model.User;
import com.pravdin.democassandra.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add-user")
    public User addUser(@RequestBody User user){
        userRepository.save(user);
        return user;
    }

    @GetMapping("/all-user")
    public List<User> getAllUser(){
        List<User> userList = userRepository.findAll();
        return userList;
    }
}
