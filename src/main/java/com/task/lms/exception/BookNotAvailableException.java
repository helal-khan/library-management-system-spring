package com.task.lms.exception;

public class BookNotAvailableException extends RuntimeException{
    public BookNotAvailableException(String message)  {
        super(message);
    }
}
