package com.task.lms.validator;

import com.task.lms.dto.BorrowRequest;
import com.task.lms.repository.BorrowRepository;
import com.task.lms.util.MyMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class BorrowValidator implements Validator {

    private final MyMessage msg;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(BorrowRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BorrowRequest borrowRequest = (BorrowRequest) target;

        if (borrowRequest.getCopies() == null) {
            errors.rejectValue("copies", "copies.is.required", msg.get("borrow.copies.required"));
        }
        if (borrowRequest.getIssueDate() == null) {
            errors.rejectValue("issueDate", "issueDate.is.required", msg.get("borrow.issueDate.required"));
        }
        if (borrowRequest.getReturnDate() == null) {
            errors.rejectValue("returnDate", "returnDate.is.required", msg.get("borrow.returnDate.required"));
        }
        if (borrowRequest.getUser() == null) {
            errors.rejectValue("user", "user.is.required", msg.get("borrow.user.required"));
        }
        if (borrowRequest.getUser() != null && borrowRequest.getUser().getId() == null) {
            errors.rejectValue("user.id", "user.id.is.required", msg.get("borrow.user.id.required"));
        }
        if (borrowRequest.getBook() == null) {
            errors.rejectValue("book", "book.is.required", msg.get("borrow.book.required"));
        }
        if (borrowRequest.getBook() != null && borrowRequest.getBook().getId() == null) {
            errors.rejectValue("book.id", "book.id.is.required", msg.get("borrow.book.id.required"));
        }
    }
}
