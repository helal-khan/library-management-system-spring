package com.task.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMetaDTO {
    private String author;
    private String publisher;
    private String edition;
    private String isbn;
}