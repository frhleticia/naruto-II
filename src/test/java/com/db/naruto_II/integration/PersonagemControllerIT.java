package com.db.naruto_II.integration;

import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.NinjaDeNinjutsu;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.repository.PersonagemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PersonagemControllerIT {

    @Autowired
    private PersonagemRepository repository;

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

        Personagem personagem = new NinjaDeNinjutsu("Naruto", 100);
        personagem = repository.save(personagem);

        JutsuRequest jutsuRequest = new JutsuRequest("Chidori", 50, 15);

        mockMvc.perform(
                        post("/personagens/" + personagem.getId() + "/jutsus")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(jutsuRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dano").value(50));
    }

    @Test
    void deveAumentarChakraDoPersonagem() throws Exception {

        Personagem personagem = new NinjaDeNinjutsu("Naruto", 100);
        personagem = repository.save(personagem);

        int quantidadeChakra = 20;

        mockMvc.perform(
                        post("/personagens/" + personagem.getId() + "/chakra")
                                .param("quantidadeChakra", String.valueOf(quantidadeChakra))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chakra").value(120));
    }

    @Test
    void deveBuscarPersonagemPorId() throws Exception {

        Personagem personagem = new NinjaDeNinjutsu("Naruto", 100);
        personagem = repository.save(personagem);

        mockMvc.perform(
                        get("/personagens/" + personagem.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Naruto"))
                .andExpect(jsonPath("$.vida").value(100));
    }

    @Test
    void deveDeletarPersonagem() throws Exception {

        Personagem personagem = new NinjaDeNinjutsu("Naruto", 100);
        personagem = repository.save(personagem);

        mockMvc.perform(
                        delete("/personagens/" + personagem.getId())
                )
                .andExpect(status().isNoContent());
    }
}
