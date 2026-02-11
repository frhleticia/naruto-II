package com.db.naruto_II.integration;

import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.NinjaDeNinjutsu;
import com.db.naruto_II.entity.NinjaDeTaijutsu;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.repository.PersonagemRepository;
import com.db.naruto_II.service.PersonagemService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PersonagemServiceIT {

    @Autowired
    private PersonagemService personagemService;

    @Autowired
    private PersonagemRepository personagemRepository;

    @Test
    void deveCriarNinjadeTaijutsuQuandoDadosValidos() {
        var resultado = personagemService.criarPersonagem(
                new PersonagemRequest("Rock Lee", 100, "Taijutsu")
        );

        assertInstanceOf(NinjaDeTaijutsu.class, resultado);
    }

    @Test
    void deveCriarNinjadeNinjutsuQuandoDadosValidos() {
        var resultado = personagemService.criarPersonagem(
                new PersonagemRequest("Sasuke", 100, "Ninjutsu")
        );

        assertInstanceOf(NinjaDeNinjutsu.class, resultado);
    }

    @Test
    void deveSalvarPersonagemDevidamenteQuandoDadosValidos() {
        var resultado = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 100, "Ninjutsu")
        );

        Personagem salvo = personagemRepository.findById(resultado.getId()).orElseThrow();

        assertNotNull(salvo);
        assertNotNull(resultado.getId());
        assertEquals("Naruto", salvo.getNome());
        assertEquals(100, salvo.getChakra());
    }

    @Test
    void deveLancarExcecaoQuandoTipoNinjaInvalido() {
        var req = new PersonagemRequest("Sakura", 100, "Genjutsu");

        assertThrows(RuntimeException.class,
                () -> personagemService.criarPersonagem(req));
    }

    @Test
    void deveLancararExcecaoQuandoNinjaNulo() {
        var req = new PersonagemRequest("Kakashi", 100, "");

        assertThrows(RuntimeException.class,
                () -> personagemService.criarPersonagem(req));
    }

    @Test
    void deveSalvarJutsuNoBancoQuandoDadosValidos() {
        var personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 100, "Ninjutsu")
        );

        personagemService.adicionarJutsu(personagem.getId(),
                new JutsuRequest("Rasengan", 30, 20)
        );

        var salvo = personagemRepository.findById(personagem.getId()).orElseThrow();

        assertEquals(1, salvo.getJutsus().size());
    }

    @Test
    void deveLancararExcecaoQuandoAdicionarJutsuDuplicado() {
        var personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 100, "Ninjutsu")
        );

        personagemService.adicionarJutsu(personagem.getId(),
                new JutsuRequest("Chidori", 30, 20)
        );

        assertThrows(RuntimeException.class,
                () -> personagemService.adicionarJutsu(personagem.getId(),
                        new JutsuRequest("Chidori", 30, 20)));
    }

    @Test
    void deveLancarExcecaoQuandoAdicionarJutsuAPersonagemInexistente() {
        assertThrows(RuntimeException.class,
                () -> personagemService.adicionarJutsu(999,
                        new JutsuRequest("Rasengan", 30, 20)));
    }

    @Test
    void deveAumentarChakraQuandoQuantidadeValida() {
        var personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Sasuke", 100, "Ninjutsu")
        );

        personagemService.aumentarChakra(personagem.getId(), 50);

        var salvo = personagemRepository.findById(personagem.getId()).orElseThrow();

        assertEquals(150, salvo.getChakra());
    }

    @Test
    void deveLancarExcecaoQuandoAumentarChakraComQuantidadeInvalida() {
        var personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Sasuke", 100, "Ninjutsu")
        );

        assertThrows(RuntimeException.class,
                () -> personagemService.aumentarChakra(personagem.getId(), -10));
    }

    @Test
    void deveLancarExcecaoQuandoAumentarChakraDePersonagemInexistente() {
        assertThrows(RuntimeException.class,
                () -> personagemService.aumentarChakra(999, 50));
    }

    @Test
    void deveDevolverPersonagemQuandoBuscarPorId() {
        var resultado = personagemService.criarPersonagem(
                new PersonagemRequest("Sasuke", 100, "Ninjutsu")
        );

        Personagem encontrado = personagemService.buscarPersonagemPorId(resultado.getId());

        assertNotNull(encontrado);
        assertEquals(resultado.getId(), encontrado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarPersonagemInexistente() {
        assertThrows(RuntimeException.class,
                () -> personagemService.buscarPersonagemPorId(999));
    }

    @Test
    void deleDeletarPersonagemQuandoExistente() {
        var resultado = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 100, "Ninjutsu")
        );

        assertNotNull(resultado);

        personagemService.deletarPersonagem(resultado.getId());

        assertTrue(personagemRepository.findAll().isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoDeletarPersonagemInexistente() {
        assertThrows(RuntimeException.class,
                () -> personagemService.deletarPersonagem(999));
    }
}
