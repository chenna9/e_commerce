package org.jsp.service;

import java.time.LocalDateTime;

import org.jsp.dto.UserRequest;
import org.jsp.dto.UserResponse;
import org.jsp.dto.LoginRequest;
import org.jsp.dto.Role;
import org.jsp.dto.Status;
import org.jsp.dto.User;
import org.jsp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public UserResponse register(UserRequest request) {

        // Check whether email already exists
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create User object
        User user = new User();

        // Set request data
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        // Set backend values
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setCreatedDate(LocalDateTime.now());

        // Save user
        User savedUser = userRepo.save(user);

        // Convert User to UserResponse
        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());
        response.setRole(savedUser.getRole());
        response.setStatus(savedUser.getStatus());
        response.setCreatedDate(savedUser.getCreatedDate());

        return response;
        
        
    }
    public UserResponse login(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setCreatedDate(user.getCreatedDate());

        return response;
    }
}