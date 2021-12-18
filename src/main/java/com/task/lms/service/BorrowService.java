package com.task.lms.service;

import com.task.lms.dto.BorrowRequest;
import com.task.lms.entity.*;
import com.task.lms.exception.BookNotAvailableException;
import com.task.lms.exception.ResourceNotFoundException;
import com.task.lms.repository.BorrowRepository;
import com.task.lms.util.MyMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
public class BorrowService {
    private static final Integer BOOK_BORROW_LIMIT = 5;
    private final BorrowRepository borrowRepository;
    private final BookService bookService;
    private final UserService userService;
    private final MailService mailService;
    private final MyMessage msg;

    public Borrow issueBook(BorrowRequest borrowRequest){
        Book book = bookService.getBook(borrowRequest.getBook().getId());
        User user = userService.getUser(borrowRequest.getUser().getId());
        Long total = borrowRepository.sumCopiesByBook(book.getId());
        total = total==null ? 0: total;
        long availableBookCopies = (book.getCopies() - total);

        if(borrowRequest.getCopies() > availableBookCopies){
            throw new BookNotAvailableException(msg.get("borrow.error.bookCopyLimit"));
        }
        if(borrowRequest.getCopies() > BOOK_BORROW_LIMIT){
            throw new BookNotAvailableException(msg.get("borrow.error.exceedUserLimit")+ BOOK_BORROW_LIMIT);
        }
        Borrow borrow = borrowRepository.save(Borrow.builder()
                                                    .copies(borrowRequest.getCopies())
                                                    .user(user)
                                                    .book(book)
                                                    .issueDate(borrowRequest.getIssueDate())
                                                    .returnDate(borrowRequest.getReturnDate())
                                                    .isReturned(false)
                                                    .borrowedBy(userService.getAdminUser())
                                                    .createdAt(Instant.now())
                                                    .build());
        mailService.sendMail(NotificationEmail.builder()
                                                .subject("New Book Issued")
                                                .recipient(user.getEmail())
                                                .body("Dear Mr. "+user.getFullName()+", \n\nA book named '"+book.getName()+"', has been issued to you. Please return within 7 days. \n\nThanks")
                                                .build());
        return borrow;
    }

    public Borrow submitReturnBook(Long id) {
        return borrowRepository.findById(id)
                .map(borrow -> {
                    System.out.println(borrow);
                    borrow.setIsReturned(true);
                    return borrowRepository.save(borrow);
                }).orElseThrow(() -> new ResourceNotFoundException(msg.get("borrow.error.notfound")+id));
    }
}
