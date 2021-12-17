package com.task.lms.validator;

import com.task.lms.dto.UserRequest;
import com.task.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.util.SetUtils;

import java.util.Locale;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
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
            errors.rejectValue("fullName", "fullName.is.required", messageSource.getMessage("user.fullName.required", null, LocaleContextHolder.getLocale()));
        }
        if (StringUtils.isBlank(userRequest.getUsername())) {
            errors.rejectValue("username", "username.is.required", messageSource.getMessage("user.username.required", null, LocaleContextHolder.getLocale()));
        }
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            errors.rejectValue("username", "username.is.taken", messageSource.getMessage("user.username.taken", null, LocaleContextHolder.getLocale()));
        }
        if (StringUtils.isBlank(userRequest.getPassword())) {
            errors.rejectValue("password", "password.is.required", messageSource.getMessage("user.password.required", null, LocaleContextHolder.getLocale()));
        }
        if (StringUtils.isBlank(userRequest.getEmail())) {
            errors.rejectValue("email", "email.is.required", messageSource.getMessage("user.email.required", null, LocaleContextHolder.getLocale()));
        }
        if (!pattern.matcher(userRequest.getEmail()).matches()) {
            errors.rejectValue("email", "email.is.invalid", messageSource.getMessage("user.email.invalid", null, LocaleContextHolder.getLocale()));
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            errors.rejectValue("email", "email.is.exist", messageSource.getMessage("user.email.exist", null, LocaleContextHolder.getLocale()));
        }
        if (StringUtils.isBlank(userRequest.getAddress())) {
            errors.rejectValue("address", "address.is.required", messageSource.getMessage("user.address.required", null, LocaleContextHolder.getLocale()));
        }
        if (SetUtils.isEmpty(userRequest.getRoles())) {
            errors.rejectValue("roles", "roles.is.required", messageSource.getMessage("user.roles.required", null, LocaleContextHolder.getLocale()));
        }


    }
}
