package com.db.naruto_II.unit;

import com.db.naruto_II.entity.NinjaDeNinjutsu;
import com.db.naruto_II.entity.NinjaDeTaijutsu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonagemTest {

    @Test
    void deveReduzirVidaQuandoReceberDano() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Itachi", 100);
        ninja.receberDano(30);


        assertEquals(70, ninja.getVida());
    }

    @Test
    void deveReduzirChakraQuandoGastarChakra() {
        NinjaDeTaijutsu ninja = new NinjaDeTaijutsu("Rock Lee", 100);
        ninja.gastarChakra(20);

        assertEquals(80, ninja.getChakra());
    }

    @Test
    void deveLancarErroQuandoChakraInsuficiente() {
        NinjaDeTaijutsu ninja = new NinjaDeTaijutsu("Neji", 100);

        assertThrows(RuntimeException.class,
                () -> ninja.gastarChakra(150));
    }

    @Test
    void deveDevolverVerdadeiroQuandoNinjaVivo() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Gaara", 100);

        assertTrue(ninja.estaVivo());
    }

    @Test
    void deveDevolverFalsoQuandoNinjaMorre() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Hidan", 100);
        ninja.receberDano(150);

        assertFalse(ninja.estaVivo());
    }
}
