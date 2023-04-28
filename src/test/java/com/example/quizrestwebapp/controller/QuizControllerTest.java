package com.example.quizrestwebapp.controller;

import com.example.quizrestwebapp.assembler.QuizModelAssembler;
import com.example.quizrestwebapp.domain.Question;
import com.example.quizrestwebapp.domain.Quiz;
import com.example.quizrestwebapp.dto.JwtRequest;
import com.example.quizrestwebapp.dto.JwtResponse;
import com.example.quizrestwebapp.exception.QuizNotFoundException;
import com.example.quizrestwebapp.repository.QuizRepository;
import com.example.quizrestwebapp.service.QuizService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Autowired
    private QuizService quizService;
    private String accessToken;
    private String refreshToken;

    private List<Quiz> shouldBeQuizzes;


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
        this.shouldBeQuizzes = repository.findAll();


    }

    @Test
    public void allTest() throws Exception {

        assertTrue(shouldBeQuizzes.size() >= 2, "Database isn't properly initialized for test");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseBody);
        JsonNode actualQuizzes = responseJson.get("_embedded").get("quizzes");
        for (int i = 0; i < shouldBeQuizzes.size(); i++) {
            assertEquals(shouldBeQuizzes.get(i).getTitle(), actualQuizzes.get(i).get("title").asText());
        }
    }

    @Test
    public void getQuestionByQuizIdTest() throws Exception {
        // Assume there is at least one quiz with ID 1 in the database
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/1/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseBody);
        Question question = quizService.getQuestion(1L, 1L);
        assertEquals(question.getBody(), responseJson.get("_embedded").get("questions").get(0).get("body").asText());

        }

}
