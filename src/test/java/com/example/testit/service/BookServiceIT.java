package com.example.testit.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;

@SpringBootTest
class BookServiceIT {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void cleanUp() {
        bookRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void borrowThenReturnBook_updatesStatusInDatabase() {
        // Given
        Member member = memberRepository.save(new Member("kikyo"));
        Book book = bookRepository.save(new Book("nett", "Ro"));
        // When : emprunt
        bookService.borrowBook(book.getId(), member.getId());
     
        Book borroowed = bookRepository.findById(book.getId()).orElseThrow();
        assertThat(borroowed.getStatus()).isEqualTo(Status.EMPRUNTE);
        assertThat(bookRepository.findByBorrowerId(member.getId())).hasSize(1);
        // When 
        bookService.returnBook(book.getId(), member.getId());
        // Then
        Book returned = bookRepository.findById(book.getId()).orElseThrow();
        assertThat(returned.getStatus()).isEqualTo(Status.RENDU);
    }
}