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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Borrow {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    //userud borrowed to
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    //bookid
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    //no of borrowed copies
    @NotNull(message = "No of copies is required")
    private int copies;

    //borroweeDate or release date
    private Instant borrowDate;

    //returnDate or due date
    private Instant returnDate;

    //borrowed by
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "borrowed_by", referencedColumnName = "id")
    private User borrowedBy;

    //isReturned?
    private Boolean isReturned;

    private Instant createdAt;

}
