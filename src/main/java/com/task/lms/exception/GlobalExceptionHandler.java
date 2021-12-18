package com.task.lms.exception;

import com.task.lms.dto.ErrorResponse;
import com.task.lms.util.MyMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.*;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final MyMessage msg;

    @ExceptionHandler(value = BookNotAvailableException.class)
    public ResponseEntity<?> handleBookNotAvailableException(BookNotAvailableException ex, WebRequest request) {
        ErrorResponse errorRes = ErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(msg.get("validation.error.title"))
                .errors(Arrays.asList(ex.getMessage().split("˜")))
                .path(getPath(request))
                .timestamp(new Date())
                .build();
        return new ResponseEntity<Object>(errorRes, new HttpHeaders(), errorRes.getStatus());
    }

    @ExceptionHandler(value = GlobalValidationException.class)
    public ResponseEntity<?> handleValidationException(GlobalValidationException ex, WebRequest request) {
        ErrorResponse errorRes = ErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(msg.get("validation.error.title"))
                .errors(Arrays.asList(ex.getMessage().split("˜")))
                .path(getPath(request))
                .timestamp(new Date())
                .build();
        return new ResponseEntity<Object>(errorRes, new HttpHeaders(), errorRes.getStatus());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorRes = ErrorResponse
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getLocalizedMessage())
                .errors(List.of(msg.get("resourceNotFound.error.title")))
                .path(getPath(request))
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(errorRes, new HttpHeaders(), errorRes.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = ex.getName()+ " " + msg.get("validation.error.argMismatch") + " " + ex.getRequiredType().getName();
        ErrorResponse errorRes = ErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getLocalizedMessage())
                .errors(List.of(error))
                .path(getPath(request))
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(
                errorRes, new HttpHeaders(), errorRes.getStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse requestHandlingNoHandlerFound(NoHandlerFoundException ex, WebRequest request) {
        return ErrorResponse
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(msg.get("resourceNotFound.error.title"))
                .errors(List.of(ex.getLocalizedMessage()))
                .path(getPath(request))
                .timestamp(new Date())
                .build();
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGenericException(final Exception ex, WebRequest r) {
        ErrorResponse errorRes = ErrorResponse
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(msg.get("internalServerError.error.title"))
                .errors(List.of(ex.getLocalizedMessage()))
                .path(getPath(r))
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(
                errorRes, new HttpHeaders(), errorRes.getStatus());
    }

//    @ExceptionHandler(value = NullPointerException.class)
//    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request)
//    {
//        ErrorResponse errorRes = ErrorResponse
//                .builder()
//                .status(HttpStatus.NOT_ACCEPTABLE.value())
//                .message(msg.get("nullPointerException.error.title"))
//                .errors(List.of(ex.getLocalizedMessage()))
//                .path(getPath(request))
//                .timestamp(new Date())
//                .build();
//        return new ResponseEntity<Object>(
//                errorRes, new HttpHeaders(), errorRes.getStatus());
//    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).substring(4);
    }
}
