package com.task.lms.controller;

import com.task.lms.dto.BorrowRequest;
import com.task.lms.entity.Borrow;
import com.task.lms.exception.GlobalValidationException;
import com.task.lms.service.BorrowService;
import com.task.lms.validator.BorrowValidator;
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
@RequestMapping("/api/borrows")
@AllArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;
    private final BorrowValidator borrowValidator;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> issueBook(@RequestBody BorrowRequest borrowRequest, BindingResult result) {
        borrowValidator.validate(borrowRequest, result);
        if (result.hasErrors()) {
            throw new GlobalValidationException(getErrors(result));
        }
        return new ResponseEntity<>(borrowService.issueBook(borrowRequest),  HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Borrow> submitReturnBook(@PathVariable Long id) {
        return status(HttpStatus.OK).body(borrowService.submitReturnBook(id));
    }

    private List<String> getErrors(BindingResult result){
        return result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }
}
