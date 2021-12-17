package com.task.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookMeta {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Book author name is required")
    private String author;

    @Nullable
    private String publisher;

    @Nullable
    private String edition;

    @Nullable
    private String isbn;

    private Instant createdAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
