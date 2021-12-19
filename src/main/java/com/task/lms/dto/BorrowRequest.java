package com.task.lms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.lms.entity.Book;
import com.task.lms.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRequest {
    private User user;
    private Book book;
    private Integer copies;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate issueDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate returnDate;
    private Boolean isReturned;
}
