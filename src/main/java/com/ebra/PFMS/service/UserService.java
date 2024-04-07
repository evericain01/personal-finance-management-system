package com.ebra.PFMS.service;

import com.ebra.PFMS.config.PasswordUtil;
import com.ebra.PFMS.entity.User;
import com.ebra.PFMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Finding user by id
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Finding user by username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Creating a user and save to database
    public void createUser(User user) {
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }
}