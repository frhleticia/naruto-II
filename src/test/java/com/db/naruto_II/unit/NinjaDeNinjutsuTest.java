package com.db.naruto_II.unit;

import com.db.naruto_II.entity.Jutsu;
import com.db.naruto_II.entity.NinjaDeNinjutsu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
public class NinjaDeNinjutsuTest {

    @Test
    void deveGastarChakraAoUsarJutsu() {

        var atacante = new NinjaDeNinjutsu("Naruto", 100);

        Jutsu jutsu = new Jutsu(30, 10);
        atacante.getJutsus().put("Rasengan", jutsu);

        assertEquals(100, atacante.getChakra());

        atacante.usarJutsu(atacante.getJutsus().get("Rasengan"));

        assertEquals(90, atacante.getChakra());
    }

    @Test
    void deveDesviarDoAtaque() {

        var ninja = spy(new NinjaDeNinjutsu("Naruto", 100));

        doReturn(0.3).when(ninja).gerarChance();

        assertTrue(ninja.desviar());
    }

    @Test
    void naoDeveDesviarDoAtaque() {

        var ninja = spy(new NinjaDeNinjutsu("Naruto", 100));

        doReturn(0.4).when(ninja).gerarChance();

        assertFalse(ninja.desviar());
    }
}
