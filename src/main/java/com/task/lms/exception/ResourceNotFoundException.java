package com.task.lms.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message)  {
        super(message);
    }
}
