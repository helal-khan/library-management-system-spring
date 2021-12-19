package com.task.lms.service;

import com.task.lms.dto.UserRequest;
import com.task.lms.entity.ERole;
import com.task.lms.entity.Role;
import com.task.lms.entity.User;
import com.task.lms.exception.ResourceNotFoundException;
import com.task.lms.repository.BorrowRepository;
import com.task.lms.repository.RoleRepository;
import com.task.lms.repository.UserRepository;
import com.task.lms.util.MyMessage;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BorrowRepository borrowRepository;
    private final MyMessage msg;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByIssuedBook(Long bookId) {
        return borrowRepository.issuedToUsersByBook(bookId);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(msg.get("user.error.notfound")+id));
    }

    @Transactional(readOnly = true)
    public User getAdminUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new ResourceNotFoundException(msg.get("user.error.notfound")));
    }

    public User createUser(UserRequest userRequest){
        User user = new User();
        user.setFullName(userRequest.getFullName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setRoles(this.getRoles(userRequest.getRoles()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFullName (userRequest.getFullName());
                    user.setAddress (userRequest.getAddress());
                    user.setRoles (this.getRoles(userRequest.getRoles()));
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException(msg.get("user.error.notfound")+id));
    }

    public String deleteUser(Long id) {
        System.out.println(id);
        return userRepository.findById(id)
                .map(user -> {
                    System.out.println(user);
                    userRepository.delete(user);
                    return msg.get("user.delete.success")+id;
                }).orElseThrow(() -> new ResourceNotFoundException(msg.get("user.error.notfound")+id));
    }

    private Set<Role> getRoles(Set<String> roles_){
        Set<Role> roles = new HashSet<>();
        //Find set of roles by string of roles ["admin", "user"]
        if (roles_ == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() ->  new ResourceNotFoundException(msg.get("role.error.notfound")));
            roles.add(userRole);
        } else {
            roles_.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException(msg.get("role.error.notfound")));
                        roles.add(adminRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new ResourceNotFoundException(msg.get("role.error.notfound")));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }
}