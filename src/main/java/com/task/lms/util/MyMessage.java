package com.task.lms.util;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MyMessage {
    private  final MessageSource messageSource;
    public String get(@NonNull String msg){
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }
}
