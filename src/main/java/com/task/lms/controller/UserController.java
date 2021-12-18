package com.task.lms.controller;

import com.task.lms.dto.UserRequest;
import com.task.lms.entity.User;
import com.task.lms.exception.GlobalValidationException;
import com.task.lms.service.UserService;
import com.task.lms.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserValidator userValidator;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return status(HttpStatus.OK).body(userService.getUser(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest, BindingResult result) {
        userValidator.validate(userRequest, result);
        if (result.hasErrors()) {
            throw new GlobalValidationException(getErrors(result));
        }
        return new ResponseEntity<>(userService.createUser(userRequest),  HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest, BindingResult result) {
        userValidator.validate(userRequest, result);
        if (result.hasErrors()) {
            throw new GlobalValidationException(getErrors(result));
        }
        return status(HttpStatus.OK).body(userService.updateUser(id, userRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return status(HttpStatus.OK).body(userService.deleteUser(id));
    }

    private List<String> getErrors(BindingResult result){
        return result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }
}
