package com.task.lms.validator;

import com.task.lms.dto.UserRequest;
import com.task.lms.repository.UserRepository;
import com.task.lms.util.MyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.util.SetUtils;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserUpdateValidator implements Validator {

    private final UserRepository userRepository;
    private final MyMessage msg;
    private final String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    Pattern pattern = Pattern.compile(regex);

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequest userRequest = (UserRequest) target;

        if (StringUtils.isBlank(userRequest.getFullName())) {
            errors.rejectValue("fullName", "fullName.is.required", msg.get("user.fullName.required"));
        }
        if (StringUtils.isBlank(userRequest.getUsername())) {
            errors.rejectValue("username", "username.is.required", msg.get("user.username.required"));
        }
        if (StringUtils.isBlank(userRequest.getEmail())) {
            errors.rejectValue("email", "email.is.required", msg.get("user.email.required"));
        }
        if (!pattern.matcher(userRequest.getEmail()).matches()) {
            errors.rejectValue("email", "email.is.invalid", msg.get("user.email.invalid"));
        }
        if (StringUtils.isBlank(userRequest.getAddress())) {
            errors.rejectValue("address", "address.is.required", msg.get("user.address.required"));
        }
        if (SetUtils.isEmpty(userRequest.getRoles())) {
            errors.rejectValue("roles", "roles.is.required", msg.get("user.roles.required"));
        }
    }
}
