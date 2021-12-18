package com.task.lms.validator;

import com.task.lms.dto.BookRequest;
import com.task.lms.repository.BookRepository;
import com.task.lms.util.MyMessage;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class BookValidator implements Validator {

    private final BookRepository bookRepository;
    private final MyMessage msg;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(BookRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookRequest bookRequest = (BookRequest) target;

        if (StringUtils.isBlank(bookRequest.getTitle())) {
            errors.rejectValue("title", "title.is.required", msg.get("book.title.required"));
        }
        if (StringUtils.isBlank(bookRequest.getName())) {
            errors.rejectValue("name", "name.is.required", msg.get("book.name.required"));
        }
        if (bookRepository.existsByName(bookRequest.getName())) {
            errors.rejectValue("name", "name.is.exist", msg.get("book.name.exist"));
        }
        if (bookRequest.getCopies() == null) {
            errors.rejectValue("copies", "copies.is.required", msg.get("book.copies.required"));
        }
        if (bookRequest.getBookMeta() == null) {
            errors.rejectValue("bookMeta", "bookMeta.is.required", msg.get("book.bookMeta.required"));
        }
        if (bookRequest.getBookMeta() != null && bookRequest.getBookMeta().getAuthor() == null) {
            errors.rejectValue("bookMeta.author", "bookMeta.author.is.required", msg.get("book.bookMeta.author.required"));
        }
    }
}
