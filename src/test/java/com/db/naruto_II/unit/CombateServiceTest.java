package com.db.naruto_II.unit;

import com.db.naruto_II.entity.Jutsu;
import com.db.naruto_II.entity.NinjaDeNinjutsu;
import com.db.naruto_II.entity.NinjaDeTaijutsu;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.service.CombateService;
import com.db.naruto_II.service.PersonagemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CombateServiceTest {

    @Mock
    private PersonagemService personagemService;

    @InjectMocks
    private CombateService combateService;

    @Test
    void deveEncontrarJutsuPeloNome() {
        Personagem personagem = new NinjaDeNinjutsu("Naruto", 100);
        Jutsu rasengan = new Jutsu(50, 10);
        personagem.getJutsus().put("rasengan", rasengan);

        when(personagemService.buscarPersonagemPorId(personagem.getId())).thenReturn(personagem);

        Jutsu resultado = combateService.encontrarJutsuPeloNome(personagem.getId(), "Rasengan");

        assertNotNull(resultado);
        assertEquals(50, resultado.getDano());
        assertEquals(10, resultado.getConsumoDeChakra());
    }

    @Test
    void deveLancarExcecaoQuandoPersonagemNaoTiverJutsus() {
        NinjaDeTaijutsu personagem = new NinjaDeTaijutsu("Sasuke", 100);

        assertThrows(RuntimeException.class,
                () -> combateService.encontrarJutsuPeloNome(personagem.getId(), "Rasengan"));
    }

    @Test
    void deveLancarExcecaoQuandoJutsuNaoEncontrado() {
        Personagem personagem = new NinjaDeNinjutsu("Naruto", 100);
        Jutsu rasengan = new Jutsu(50, 10);
        personagem.getJutsus().put("rasengan", rasengan);

        assertThrows(RuntimeException.class,
                () -> combateService.encontrarJutsuPeloNome(personagem.getId(), "Chidori"));
    }

    @Test
    void deveValidarPersonagemVivo() {
        Personagem personagem = new NinjaDeNinjutsu("Sakura", 100);
        personagem.setId(1);

        when(personagemService.buscarPersonagemPorId(1))
                .thenReturn(personagem);

        Personagem resultado =
                combateService.validarPersonagemVivo(1);

        assertNotNull(resultado);
        assertTrue(resultado.estaVivo());
    }

    @Test
    void deveLancarExcecaoQuandoPersonagemMorto() {
        Personagem personagem = new NinjaDeTaijutsu("Hidan", 0);

        assertThrows(RuntimeException.class,
                () -> combateService.validarPersonagemVivo(personagem.getId()));
    }

    @Test
    void deveAtacarComJutsuSemDesvio() {
        Personagem atacante = new NinjaDeNinjutsu("Naruto", 100);
        Personagem defensor = spy(new NinjaDeTaijutsu("Sasuke", 100));
        doReturn(0.99).when(defensor).gerarChance();

        assertTrue(defensor.desviar());

        atacante.setId(1);
        defensor.setId(2);

        when(personagemService.buscarPersonagemPorId(1)).thenReturn(atacante);
        when(personagemService.buscarPersonagemPorId(2)).thenReturn(defensor);

        Jutsu rasengan = new Jutsu(50, 10);
        atacante.getJutsus().put("rasengan", rasengan);

        combateService.atacarComJutsu(1, 2, "rasengan");

        assertEquals(50, defensor.getVida());
        assertEquals(90, atacante.getChakra());
    }

    @Test
    void deveAtacarComJutsuComDesvio() {
        Personagem atacante = new NinjaDeNinjutsu("Naruto", 100);
        Personagem defensor = spy(new NinjaDeTaijutsu("Sasuke", 100));
        doReturn(0.10).when(defensor).gerarChance();

        assertTrue(defensor.desviar());

        atacante.setId(1);
        defensor.setId(2);

        when(personagemService.buscarPersonagemPorId(1)).thenReturn(atacante);
        when(personagemService.buscarPersonagemPorId(2)).thenReturn(defensor);

        Jutsu rasengan = new Jutsu(50, 10);
        atacante.getJutsus().put("rasengan", rasengan);

        combateService.atacarComJutsu(1, 2, "rasengan");

        assertEquals(100, defensor.getVida());
        assertEquals(90, atacante.getChakra());
    }


    @Test
    void deveLancarExcecaoQuandoAtacarComJutsuInexistente() {
        Personagem atacante = new NinjaDeTaijutsu("Sasuke", 100);
        Personagem defensor = new NinjaDeTaijutsu("Naruto", 100);

        assertThrows(RuntimeException.class,
                () -> combateService.atacarComJutsu(atacante.getId(), defensor.getId(), "Chidori"));
    }

    @Test
    void deveLancarExcecaoQuandoAtacarPersonagemMorto() {
        Personagem atacante = new NinjaDeNinjutsu("Hidan", 0);
        Personagem defensor = new NinjaDeNinjutsu("Sakura", 100);
        Jutsu rasengan = new Jutsu(50, 10);
        atacante.getJutsus().put("rasengan", rasengan);

        assertThrows(RuntimeException.class,
                () -> combateService.atacarComJutsu(atacante.getId(), defensor.getId(), "rasengan"));
    }
}
