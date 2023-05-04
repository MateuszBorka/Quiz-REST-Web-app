package com.example.quizrestwebapp.controller;

import com.example.quizrestwebapp.dto.JwtRequest;
import com.example.quizrestwebapp.dto.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testLogin() throws Exception {      //content inside startWith needs to be replaced when main token is changed
        JwtRequest jwtRequest = new JwtRequest("anton", "1234");


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andReturn();


        JwtResponse actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), JwtResponse.class);
        assertTrue(actualResponse.getAccessToken().startsWith("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnRvbiIsImV4cCI6MTY4")
                && actualResponse.getRefreshToken().startsWith("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnRvbiIsImV4cCI6MTY4"));
    }

}
