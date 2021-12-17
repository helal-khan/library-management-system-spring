package com.task.lms.exception;

public class SpringGlobalException extends RuntimeException{
    public SpringGlobalException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringGlobalException(String exMessage) {
        super(exMessage);
    }
}
