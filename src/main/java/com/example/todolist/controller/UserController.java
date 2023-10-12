package com.example.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public UserModel create(@RequestBody UserModel userModel){
        UserModel user = this.userRepository.findByUsername(userModel.getUsername());
        UserModel userCreated = this.userRepository.save(userModel);
        return userCreated;
    }
}
