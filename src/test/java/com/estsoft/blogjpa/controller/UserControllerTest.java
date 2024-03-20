package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.domain.User;
import com.estsoft.blogjpa.dto.AddUserRequest;
import com.estsoft.blogjpa.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext ac;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ac).build();
    }

    @Test
    void signup() throws Exception {
        // given : 회원가입에 필요한 정보 초기화
        AddUserRequest request = new AddUserRequest("mock_email", "mock_pw");
        /** AddUserRequest 클래스에서 생성자를 만들어도 되지만. 생성자 없이
         * 이렇게도 가능
         *         AddUserRequest request = new AddUserRequest();
         *         request.setEmail("mock_email");
         *         request.setPassword("mock_pw");
         */

        //userRepository.save(new User(request.getEmail(), request.getPassword()));

        // when : POST /user
        ResultActions response = mockMvc.perform(post("/user")
                .param("email", request.getEmail())
                .param("password", request.getPassword()));

        // then : 호출 결과 HTTP Status code 200 으로 검증 가능
//        response.andExpect(status().is4xxClientError());
        response.andExpect(status().is3xxRedirection());
        User byEmail = userRepository.findByEmail(request.getEmail()).orElseThrow();
        Assertions.assertNotNull(byEmail);
    }
}