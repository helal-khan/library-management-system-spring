package com.task.lms.controller;

import com.task.lms.dto.ErrorResponse;
import com.task.lms.dto.UserRequest;
import com.task.lms.entity.User;
import com.task.lms.service.UserService;
import com.task.lms.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserValidator userValidator;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest, BindingResult result) {
        userValidator.validate(userRequest, result);
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Validation failure", errors));
        }
        User user = userService.createUser(userRequest);
        return new ResponseEntity<>(user,  HttpStatus.OK);
    }
}
