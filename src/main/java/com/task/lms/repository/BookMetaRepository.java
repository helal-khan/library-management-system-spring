package com.task.lms.repository;

import com.task.lms.entity.BookMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMetaRepository extends JpaRepository<BookMeta, Long> {
}
