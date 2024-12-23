package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.Product;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // Create User
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update User
    public User updateUser(Long id, User updatedUser) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    // Delete User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Create Product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Update Product
    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found");
    }

    // Delete Product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
