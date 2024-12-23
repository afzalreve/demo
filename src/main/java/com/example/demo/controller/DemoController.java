package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Product;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    private DemoService demoService;

    // Create User
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return demoService.createUser(user);
    }

    // Update User
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return demoService.updateUser(id, user);
    }

    // Delete User
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        demoService.deleteUser(id);
    }

    // Create Product
    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return demoService.createProduct(product);
    }

    // Update Product
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return demoService.updateProduct(id, product);
    }

    // Delete Product
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        demoService.deleteProduct(id);
    }
}
