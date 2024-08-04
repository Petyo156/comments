package com.tinqinacademy.comments.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.models.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.models.leavecommentforroom.LeaveCommentForRoomOutput;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HotelControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenRoomId_whenGetComments_thenReturnList() throws Exception {
        String roomId = "1";
        mockMvc.perform(get(RestApiMapping.GET_PATH, roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").exists())
                .andExpect(jsonPath("$.list[0].firstName").value("petar"));
    }

    @Test
    public void leaveCommentForRoomTest() throws Exception {
        String roomId = "1";
        LeaveCommentForRoomInput input = LeaveCommentForRoomInput.builder()
                .roomId("1")
                .content("1")
                .firstName("petar")
                .lastName("petrov")
                .build();

        mockMvc.perform(post(RestApiMapping.POST_PATH, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }


}
