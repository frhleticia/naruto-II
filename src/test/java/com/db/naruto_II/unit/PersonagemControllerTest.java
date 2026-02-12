package com.db.naruto_II.unit;

import com.db.naruto_II.controller.PersonagemController;
import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.Jutsu;
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

import static org.junit.jupiter.api.Assertions.*;
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
        assertNotNull(response.getBody());
        assertEquals("Naruto", response.getBody().getNome());

        verify(service).criarPersonagem(request);
    }

    @Test
    void deveAdicionarJutsuAoPersonagem() {
        Integer id = 1;
        JutsuRequest request = new JutsuRequest("Chidori", 50, 15);

        Jutsu jutsu = new Jutsu();
        jutsu.setDano(50);
        jutsu.setConsumoDeChakra(15);

        when(service.adicionarJutsu(id, request)).thenReturn(jutsu);

        ResponseEntity<Jutsu> response = controller.adicionarJutsu(id, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(50, response.getBody().getDano());
        assertEquals(15, response.getBody().getConsumoDeChakra());

        verify(service).adicionarJutsu(id, request);
    }

    @Test
    void deveLancarExcecaoQuandoAdicionarJutsuAPersonagemInexistente() {
        Integer id = 999;
        JutsuRequest request = new JutsuRequest("Chidori", 50, 15);

        when(service.adicionarJutsu(id, request))
                .thenThrow(new RuntimeException("Personagem não encontrado"));

        assertThrows(RuntimeException.class,
                () -> controller.adicionarJutsu(id, request));

        verify(service).adicionarJutsu(id, request);
    }

    @Test
    void deveAumentarChakraDoPersonagem() {
        Integer id = 1;
        int quantidade = 20;

        Personagem personagem = new NinjaDeNinjutsu("Naruto", 120);
        when(service.aumentarChakra(id, quantidade)).thenReturn(personagem);

        ResponseEntity<Personagem> response = controller.aumentarChakra(id, quantidade);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(120, response.getBody().getChakra());
    }

    @Test
    void deveBuscarPersonagemPorId() {
        Integer id = 1;
        Personagem personagem = new NinjaDeNinjutsu("Sasuke", 100);
        personagem.setId(id);

        when(service.buscarPersonagemPorId(id)).thenReturn(personagem);

        ResponseEntity<Personagem> response = controller.buscarPersonagemPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("Sasuke", response.getBody().getNome());

        verify(service).buscarPersonagemPorId(id);
    }

    @Test
    void deveDeletarPersonagem() {
        Integer id = 1;

        ResponseEntity<Void> response = controller.deletarPersonagem(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service).deletarPersonagem(id);
    }
}
