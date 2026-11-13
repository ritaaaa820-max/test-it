package com.example.testit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;

@SpringBootTest
@Transactional
class BookRepositoryIT {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByMemberAndStatus_returnsOnlyBooksOfMemberWithStatus() {
        // Given
        Member memberOne = memberRepository.save(new Member("kikyo"));
        Member memberTwo = memberRepository.save(new Member("rubi"));

        Book bookOne = new Book("tt1", "auth1");
        bookOne.setStatus(Status.EMPRUNTE);
        bookOne.setBorrower(memberOne);

        Book bookTwo = new Book("tt2", "auth1");
        bookTwo.setStatus(Status.EMPRUNTE);
        bookTwo.setBorrower(memberTwo);

        Book bookThree = new Book("tt3", "auth3");
        bookThree.setStatus(Status.DISPONIBLE);
        bookThree.setBorrower(memberOne);

        bookRepository.saveAll(List.of(bookOne, bookTwo, bookThree));

        // When
        List<Book> result = bookRepository.findByMemberAndStatus(memberOne.getId(), Status.EMPRUNTE);

        // Then
        assertThat(result)
                .extracting(Book::getTitle)
                .containsExactly("tt1");
    }
}