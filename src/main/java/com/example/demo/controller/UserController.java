package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void saveUser( @RequestBody UserDTO userDTO) {
        System.out.println("Received user: " + userDTO);
        userService.saveUser(userDTO);
    }

    @PostMapping("/update")
    public void updateUser( @RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
    }

}
