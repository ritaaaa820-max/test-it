package com.example.testit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.testit.adapter.member.CurrentMemberServiceFake;
import com.example.testit.model.Member;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CurrentMemberServiceFake currentMemberService;

    @AfterEach
    void cleanUp() {
        bookRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void createBookThenBorrowIt() throws Exception {
        // Un membre connecté
        Member member = memberRepository.save(new Member("kikyo"));
        currentMemberService.setCurrent(member.getId());

        // Créer un livre
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"nett\",\"author\":\"Ro\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Ro"));

        // Récupérer son id
        Long bookId = bookRepository.findAll().get(0).getId();

        // L'emprunter
        mockMvc.perform(post("/books/" + bookId + "/borrow"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("EMPRUNTE"));
    }
}