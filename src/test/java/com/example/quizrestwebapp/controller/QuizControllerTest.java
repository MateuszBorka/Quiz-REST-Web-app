package com.example.quizrestwebapp.controller;

import com.example.quizrestwebapp.assembler.QuizModelAssembler;
import com.example.quizrestwebapp.domain.Quiz;
import com.example.quizrestwebapp.dto.JwtRequest;
import com.example.quizrestwebapp.dto.JwtResponse;
import com.example.quizrestwebapp.repository.QuizRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QuizRepository repository;
    @Autowired
    private QuizModelAssembler quizAssembler;
    private String accessToken;
    private String refreshToken;



    @BeforeEach
    public void setup() throws Exception {

        JwtRequest jwtRequest = new JwtRequest("anton", "1234");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andReturn();

        JwtResponse actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), JwtResponse.class);
        this.accessToken = actualResponse.getAccessToken();
        this.refreshToken = actualResponse.getRefreshToken();



    }

    @Test
    public void testAll() throws Exception{

        List<Quiz> shouldBeQuizzes = repository.findAll();
        assertTrue(shouldBeQuizzes.size() >= 2, "Database isn't properly initialized for test");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseBody);
        JsonNode actualQuizzes = responseJson.get("_embedded").get("quizzes");
        System.out.println("Hello world!");
        for(int i = 0; i < shouldBeQuizzes.size(); i++){
            assertTrue(Objects.equals(shouldBeQuizzes.get(i).getTitle(), actualQuizzes.get(i).get("title").asText()));
        }
    }

}
