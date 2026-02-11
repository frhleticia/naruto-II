package com.db.naruto_II.unit;

import com.db.naruto_II.dto.PersonagemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonagemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void deveCriarPersonagem() throws Exception {
        var request = new PersonagemRequest("Naruto", 100, "Ninjutsu");

        String ninjaRequest = mapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/personagens")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ninjaRequest)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Naruto"))
                .andExpect(jsonPath("$.vida").value(100));
    }
}
