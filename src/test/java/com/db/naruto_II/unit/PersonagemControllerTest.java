package com.db.naruto_II.unit;

import com.db.naruto_II.controller.PersonagemController;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.NinjaDeNinjutsu;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.service.PersonagemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonagemControllerTest {

    @InjectMocks
    private PersonagemController controller;

    @Mock
    private PersonagemService service;

    @Test
    void deveCriarPersonagem() {

        var request = new PersonagemRequest("Naruto", 100, "Ninjutsu");
        NinjaDeNinjutsu personagem = new NinjaDeNinjutsu("Naruto", 100);
        personagem.setId(1);

        when(service.criarPersonagem(request)).thenReturn(personagem);

        ResponseEntity<Personagem> response = controller.criarPersonagem(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Naruto", response.getBody().getNome());

        verify(service).criarPersonagem(request);
    }
}
