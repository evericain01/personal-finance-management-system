package com.ebra.PFMS.config;

import com.ebra.PFMS.entity.User;
import com.ebra.PFMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Component
@Profile("!test")
public class LoadUsers implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadUserData();
    }

    // Loading initial user data (the password will be hashed before saving to the database)
    private void loadUserData() {
        addUser("admin", "admin@example.com", "adminpass");
        addUser("user1", "user1@example.com", "user1pass");
    }

    // Adding a new user to the database
    private User addUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(PasswordUtil.hashPassword(password));
            user.setCreatedAt(Timestamp.from(Instant.now()));
            return userRepository.save(user);
        } else {
            System.out.println("User already exists: " + username);
            return null;
        }
    }
}