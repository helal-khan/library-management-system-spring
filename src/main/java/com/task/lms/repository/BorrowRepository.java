package com.task.lms.repository;

import com.task.lms.entity.Book;
import com.task.lms.entity.Borrow;
import com.task.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query(value = "SELECT sum(b.copies) FROM Borrow b WHERE b.isReturned = false AND b.book.id = :id")
    Long sumCopiesByBook(@Param("id") Long bookId);

    @Query("SELECT distinct b.book FROM Borrow b where b.isReturned = false")
    List<Book> findIssuedBook();

    @Query("SELECT b.user FROM Borrow b where b.isReturned = false AND b.book.id = :id")
    List<User> issuedToUsersByBook(@Param("id") Long bookId);
}
