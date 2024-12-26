package com.example.demo.service;

import com.example.demo.dao.UserDAO;
import com.example.demo.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService( UserDAO userDAO ) {
        this.userDAO = userDAO;
    }

    public UserDTO saveUser( UserDTO userDTO) {
        return userDAO.saveUser(userDTO);
    }

    public UserDTO updateUser( UserDTO userDTO) {
        return userDAO.updateUser(userDTO);
    }

}
