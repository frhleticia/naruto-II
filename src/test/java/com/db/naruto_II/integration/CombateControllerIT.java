package com.db.naruto_II.integration;

import com.db.naruto_II.dto.AtaqueRequest;
import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.entity.NinjaDeNinjutsu;
import com.db.naruto_II.entity.NinjaDeTaijutsu;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CombateControllerIT {

    @Autowired
    private PersonagemRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void DeveRealizarAtaqueComJutsuSemDesvioDoDefensor() throws Exception {

        var atacante = new NinjaDeNinjutsu("Sasuke", 100);
        atacante = repository.save(atacante);

        var jutsuReq = new JutsuRequest("Chidori", 30, 10);

        mockMvc.perform(
                post("/personagens/" + atacante.getId() + "/jutsus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jutsuReq))
        ).andExpect(status().isCreated());

        var defensor = new NinjaDeTaijutsu("Naruto", 100);

        defensor = repository.save(defensor);

        var ataque = new AtaqueRequest(atacante.getId(), defensor.getId(), "Chidori");

        mockMvc.perform(
                post("/combates/atacar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ataque))
        ).andExpect(status().isNoContent());

        Personagem defensorAtualizado =
                repository.findById(defensor.getId()).orElseThrow();

        assertTrue(defensorAtualizado.getVida() == 70 ||
                        defensorAtualizado.getVida() == 100);
    }
}
