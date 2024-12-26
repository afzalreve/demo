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

    public void saveUser( UserDTO userDTO) {
        userDAO.saveUser(userDTO);
    }

    public void updateUser( UserDTO userDTO) {
        userDAO.updateUser(userDTO);
    }

}
