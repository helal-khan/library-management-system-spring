package com.task.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String title;
    private String name;
    private Integer copies;
    private BookMetaDTO bookMeta;
}