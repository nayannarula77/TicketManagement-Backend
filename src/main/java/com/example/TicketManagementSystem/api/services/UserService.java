package com.example.TicketManagementSystem.api.services;

import com.example.TicketManagementSystem.api.dao.models.Category;
import com.example.TicketManagementSystem.api.dao.models.User;
import com.example.TicketManagementSystem.api.repository.EnUserType;
import com.example.TicketManagementSystem.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserByEmail(String email) {
        List<User> users = userRepository.findAll();
        for(User user: users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByID(int id) {
        List<User> users = userRepository.findAll();
        for(User user: users) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }


    public static Category getCategoryOfMember(User user) {
        // first create groups
        return new Category();
    }
}
