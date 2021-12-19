package com.task.lms.controller;

import com.task.lms.dto.BookRequest;
import com.task.lms.entity.Book;
import com.task.lms.exception.GlobalValidationException;
import com.task.lms.service.BookService;
import com.task.lms.validator.BookUpdateValidator;
import com.task.lms.validator.BookValidator;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookValidator bookValidator;
    private final BookUpdateValidator bookUpdateValidator;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Book>> getAllBooks() {
        return status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping("/issued")
    public ResponseEntity<List<Book>> getAllBooksByIssued() {
        return status(HttpStatus.OK).body(bookService.getAllBooksByIssued());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return status(HttpStatus.OK).body(bookService.getBook(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Book>> getBooksByNameOrAuthor(@RequestParam("query") String query) {
        return status(HttpStatus.OK).body(bookService.searchByNameOrAuthor(query));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createBook(@RequestBody BookRequest bookRequest, BindingResult result) {
        bookValidator.validate(bookRequest, result);
        if (result.hasErrors()) {
            throw new GlobalValidationException(getErrors(result));
        }
        return new ResponseEntity<>(bookService.createBook(bookRequest),  HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest, BindingResult result) {
        bookUpdateValidator.validate(bookRequest, result);
        if (result.hasErrors()) {
            throw new GlobalValidationException(getErrors(result));
        }
        return status(HttpStatus.OK).body(bookService.updateBook(id, bookRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return status(HttpStatus.OK).body(bookService.deleteBook(id));
    }

    private List<String> getErrors(BindingResult result){
        return result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }
}
