package com.db.naruto_II.unit;

import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void deveAdicionarJutsuAoPersonagem() throws Exception {
        var request = new JutsuRequest("Chidori", 50, 15);

        String jutsuRequest = mapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/personagens/{id}/jutsus")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jutsuRequest)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Chidori"))
                .andExpect(jsonPath("$.dano").value(50))
                .andExpect(jsonPath("$.consumoDeChakra").value(15));
    }

    @Test
    void deveAumentarChakraDoPersonagem() throws Exception {
        int quantidadeChakra = 20;

        mockMvc.perform(
                        post("/personagens/{id}/chakra")
                                .param("quantidadeChakra", String.valueOf(quantidadeChakra))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chakra").value(quantidadeChakra));
    }

    @Test
    void deveBuscarPersonagemPorId() throws Exception {
        mockMvc.perform(
                        get("/personagens/{id}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Naruto"))
                .andExpect(jsonPath("$.vida").value(100));
    }

    @Test
    void deveDeletarPersonagem() throws Exception {
        mockMvc.perform(
                        post("/personagens/{id}")
                )
                .andExpect(status().isNoContent());
    }
}
