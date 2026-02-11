package com.db.naruto_II.integration;

import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.Jutsu;
import com.db.naruto_II.service.CombateService;
import com.db.naruto_II.service.PersonagemService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CombateServiceIT {

    @Autowired
    private CombateService combateService;

    @Autowired
    private PersonagemService personagemService;

    @Test
    void deveEncontrarJutsuPeloNome() {
        var personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 100, "Ninjutsu")
        );

        personagemService.adicionarJutsu(personagem.getId(),
                new JutsuRequest("Rasengan", 30, 20)
        );

        Jutsu resultado = combateService.encontrarJutsuPeloNome(personagem.getId(), "Rasengan");

        assertNotNull(resultado);
        assertEquals(30, resultado.getDano());
        assertEquals(20, resultado.getConsumoDeChakra());
    }

    @Test
    void deveLancarExcecaoQuandoPersonagemNaoTiverJutsus() {
        var personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Sasuke", 100, "Ninjutsu")
        );

        personagemService.adicionarJutsu(personagem.getId(),
                new JutsuRequest("Chidori", 30, 20)
        );

        assertThrows(RuntimeException.class,
                () -> combateService.encontrarJutsuPeloNome(personagem.getId(), "JutsuInexistente"));
    }

    @Test
    void deveLancarExcecaoQuandoTentarEncontrarJutsuNulo() {
        var personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 100, "Ninjutsu")
        );



        assertThrows(RuntimeException.class,
                () -> combateService.encontrarJutsuPeloNome(personagem.getId(), "Rasengan"));
    }

    @Test
    void deveLancarExcecaoQuandoPersonagemMorto() {
        var personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Sasuke", 0, "Ninjutsu")
        );

        assertThrows(RuntimeException.class,
                () -> combateService.validarPersonagemVivo(personagem.getId()));
    }

    @Test
    void deveValidarPersonagemVivo() {
        var personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Sasuke", 100, "Ninjutsu")
        );

        combateService.validarPersonagemVivo(personagem.getId());

        assertNotNull(personagem.getId());
    }

    @Test
    void deveLancarExcecaoQuandoAtacarComJutsuDePersonagemInexistente() {
        assertThrows(RuntimeException.class,
                () -> combateService.atacarComJutsu(999, 1, "Rasengan"));
    }

    @Test
    void deveAtacarComJutsuComChanceDeDesvio() {
        var atacante = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 100, "Ninjutsu"));
        var defensor = personagemService.criarPersonagem(
                new PersonagemRequest("Sasuke", 100, "Ninjutsu"));

        personagemService.adicionarJutsu(atacante.getId(),
                new JutsuRequest("Rasengan", 30, 20));

        defensor.setVida(100);
        combateService.atacarComJutsu(atacante.getId(), defensor.getId(), "Rasengan");

        int vidaInicial = defensor.getVida();

        combateService.atacarComJutsu(atacante.getId(), defensor.getId(), "Rasengan");

        var defensorSalvo = personagemService.buscarPersonagemPorId(defensor.getId());

        assertTrue(defensorSalvo.getVida() == vidaInicial ||
                defensorSalvo.getVida() == vidaInicial - 30);
    }
}
