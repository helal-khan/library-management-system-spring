package com.task.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Borrow {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @NotNull(message = "No of copies is required")
    private int copies;

    private Date issueDate;

    private Date returnDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "borrowed_by", referencedColumnName = "id")
    private User borrowedBy;

    private Boolean isReturned;

    private Instant createdAt;

}
