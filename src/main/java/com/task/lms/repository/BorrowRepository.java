package com.task.lms.repository;

import com.task.lms.entity.Book;
import com.task.lms.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query(value = "SELECT sum(b.copies) FROM Borrow b WHERE b.isReturned = false AND b.book.id = :id")
    public Long sumCopiesByBook(@Param("id") Long bookId);
}
