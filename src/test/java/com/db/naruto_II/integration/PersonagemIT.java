package com.db.naruto_II.integration;

import com.db.naruto_II.entity.Jutsu;
import com.db.naruto_II.entity.NinjaDeNinjutsu;
import com.db.naruto_II.entity.NinjaDeTaijutsu;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.repository.PersonagemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonagemIT {

    @Autowired
    private PersonagemRepository personagemRepository;

    @Test
    void deveSalvarNinjaNoBanco() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Naruto", 100);

        Personagem salvo = personagemRepository.save(ninja);

        assertNotNull(salvo.getId());
        assertEquals(100, salvo.getChakra());
        assertEquals("Naruto", salvo.getNome());
    }

    @Test
    void deveSalvarTiposDistintosNaMesmaTabela() {
        NinjaDeNinjutsu ninjutsu = new NinjaDeNinjutsu("Naruto", 100);
        NinjaDeTaijutsu taijutsu = new NinjaDeTaijutsu("Rock Lee", 100);

        personagemRepository.save(ninjutsu);
        personagemRepository.save(taijutsu);

        List<Personagem> todosPersonagensSalvos = personagemRepository.findAll();

        assertEquals(2, todosPersonagensSalvos.size());
    }

    @Test
    void deveSalvarJutsusNoMap() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Sasuke", 100);

        Jutsu jutsu = new Jutsu(80, 50);
        ninja.getJutsus().put("Chidori", jutsu);

        personagemRepository.save(ninja);

        Personagem salvo = personagemRepository.findById(ninja.getId()).orElseThrow();

        assertEquals(1, salvo.getJutsus().size());
        assertTrue(salvo.getJutsus().containsKey("Chidori"));
    }

    @Test
    void vidaNaoDeveFicarNegativa() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Sasuke", 100);
        ninja.receberDano(150);

        personagemRepository.save(ninja);

        Personagem salvo = personagemRepository.findById(ninja.getId()).orElseThrow();

        assertEquals(0, salvo.getVida());
    }

    @Test
    void deveGastarChakraDevidamente() {
        NinjaDeTaijutsu ninja = new NinjaDeTaijutsu("Naruto", 100);
        ninja.gastarChakra(30);

        personagemRepository.save(ninja);

        Personagem salvo = personagemRepository.findById(ninja.getId()).orElseThrow();

        assertEquals(70, salvo.getChakra());
    }

    @Test
    void deveLancarExcecaoAoGastarChakraInsuficiente() {
        NinjaDeTaijutsu ninja = new NinjaDeTaijutsu("Rock Lee", 100);

        assertThrows(RuntimeException.class, () -> ninja.gastarChakra(150));
    }

    @Test
    void deveVerificarSePersonagemEstaVivo() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Sasuke", 100);
        assertTrue(ninja.estaVivo());

        ninja.receberDano(150);
        assertFalse(ninja.estaVivo());
    }
}
