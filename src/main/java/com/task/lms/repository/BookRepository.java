package com.task.lms.repository;

import com.task.lms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Boolean existsByName(String name);

    @Query("SELECT b FROM Book b WHERE b.name LIKE %:name%")
    List<Book> searchByNameLike(@Param("name") String name);
}
