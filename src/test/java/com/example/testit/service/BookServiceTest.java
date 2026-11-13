package com.example.testit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.testit.adapter.mail.MailService;
import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MailService mailService;

    @InjectMocks
    private BookService bookService;

    @Test
    void borrowBook_availableBook_setsStatusAndBorrower() {
        Book book = new Book("nett", "Ro");
        Member member = new Member("kikyo");
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.borrowBook(10L, 1L);

        assertThat(result.getStatus()).isEqualTo(Status.EMPRUNTE);
        assertThat(result.getBorrower()).isSameAs(member);
        verifyNoInteractions(mailService);
    }

    @Test
    void returnBook_sendsMailToMember() {
        Member member = new Member("kikyo");
        Book book = new Book("nett", "Ro");
        book.setStatus(Status.EMPRUNTE);
        book.setBorrower(member);
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.returnBook(10L, 1L);

        assertThat(result.getStatus()).isEqualTo(Status.RENDU);
        verify(mailService).sendMail(any(Member.class), any(), any());
    }
}