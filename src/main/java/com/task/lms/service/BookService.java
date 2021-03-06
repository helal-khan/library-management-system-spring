package com.task.lms.service;

import com.task.lms.dto.BookRequest;
import com.task.lms.entity.*;
import com.task.lms.exception.ResourceNotFoundException;
import com.task.lms.repository.BookMetaRepository;
import com.task.lms.repository.BookRepository;

import com.task.lms.repository.BorrowRepository;
import com.task.lms.util.MyMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMetaRepository bookMetaRepository;
    private final BorrowRepository borrowRepository;
    private final MyMessage msg;

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooksByIssued() {
        return borrowRepository.findIssuedBook();
    }

    @Transactional(readOnly = true)
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(msg.get("book.error.notfound")+id));
    }

    @Transactional(readOnly = true)
    public List<Book> searchByNameOrAuthor(String name) {
        List<Book> books = bookRepository.searchByNameLike(name);
        if(books.isEmpty()){
            books = bookRepository.searchByAuthorLike(name);
        }
        return books;
    }

    public Book createBook(BookRequest bookRequest){
        Book book = Book.builder()
                        .title(bookRequest.getTitle())
                        .name(bookRequest.getName())
                        .copies(bookRequest.getCopies())
                        .createdAt(Instant.now())
                        .build();
        BookMeta bookMeta = BookMeta.builder()
                                    .author(bookRequest.getBookMeta().getAuthor())
                                    .publisher(bookRequest.getBookMeta().getPublisher())
                                    .edition(bookRequest.getBookMeta().getEdition())
                                    .isbn(bookRequest.getBookMeta().getIsbn())
                                    .createdAt(Instant.now())
                                    .build();
        book.setBookMeta(bookMeta);
        bookMeta.setBook(book);
        bookMetaRepository.save(bookMeta);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookRequest bookRequest) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookRequest.getTitle());
                    book.setCopies(bookRequest.getCopies());
                    BookMeta bookMeta = BookMeta.builder()
                            .id(book.getBookMeta().getId())
                            .author(bookRequest.getBookMeta().getAuthor())
                            .publisher(bookRequest.getBookMeta().getPublisher())
                            .edition(bookRequest.getBookMeta().getEdition())
                            .isbn(bookRequest.getBookMeta().getIsbn())
                            .build();
                    book.setBookMeta(bookMeta);
                    bookMeta.setBook(book);
                    bookMetaRepository.save(bookMeta);
                    return bookRepository.save(book);
                }).orElseThrow(() -> new ResourceNotFoundException(msg.get("book.error.notfound")+id));
    }

    public String deleteBook(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return msg.get("book.delete.success")+id;
                }).orElseThrow(() -> new ResourceNotFoundException(msg.get("book.error.notfound")+id));
    }
}
