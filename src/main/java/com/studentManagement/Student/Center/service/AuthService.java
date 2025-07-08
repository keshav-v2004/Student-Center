package com.studentManagement.Student.Center.service;

import com.studentManagement.Student.Center.authentication.JwtUtil;
import com.studentManagement.Student.Center.dto.AuthRequest;
import com.studentManagement.Student.Center.dto.AuthResponse;
import com.studentManagement.Student.Center.dto.RegisterRequest;
import com.studentManagement.Student.Center.entity.User;
import com.studentManagement.Student.Center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public ResponseEntity<?> register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt

        // Set role
        user.setRole(
                request.getRole() != null ? request.getRole() : "ROLE_USER"
        );

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> login(AuthRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        User user = optionalUser.get();

        //  Match raw password with encoded password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        //  Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    //delete a user (possible by admin only)
    public ResponseEntity<?> deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with username: " + username);
        }

        userRepository.delete(optionalUser.get());

        return ResponseEntity.ok("User deleted successfully");
    }

    //show all users to admin only
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
        }

        return ResponseEntity.ok(users);
    }
}
