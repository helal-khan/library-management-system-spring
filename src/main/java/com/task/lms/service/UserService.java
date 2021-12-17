package com.task.lms.service;

import com.task.lms.dto.UserRequest;
import com.task.lms.entity.ERole;
import com.task.lms.entity.Role;
import com.task.lms.entity.User;
import com.task.lms.exception.SpringGlobalException;
import com.task.lms.repository.RoleRepository;
import com.task.lms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public User createUser(UserRequest userRequest){
        User user = new User();
        user.setFullName(userRequest.getFullName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedAt(Instant.now());

        //Find roles and add to new user - ["admin", "user"]
        Set<Role> roles = new HashSet<>();
        if (userRequest.getRoles() == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new SpringGlobalException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            userRequest.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new SpringGlobalException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new SpringGlobalException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
