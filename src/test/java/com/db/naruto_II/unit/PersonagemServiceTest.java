package com.db.naruto_II.unit;

import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.NinjaDeNinjutsu;
import com.db.naruto_II.entity.NinjaDeTaijutsu;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.repository.PersonagemRepository;
import com.db.naruto_II.service.PersonagemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonagemServiceTest {

    @InjectMocks
    private PersonagemService personagemService;

    @Mock
    private PersonagemRepository personagemRepository;

    @Test
    void deveCriarNinjaTaiQuandoDadosValidos() {
        PersonagemRequest req = new PersonagemRequest("Naruto", 100, "Taijutsu");
        NinjaDeTaijutsu ninja = new NinjaDeTaijutsu("Naruto", 100);

        when(personagemRepository.save(any(Personagem.class)))
                .thenReturn(ninja);

        Personagem resultado = personagemService.criarPersonagem(req);

        assertNotNull(resultado);
        assertEquals("Naruto", resultado.getNome());
        assertInstanceOf(NinjaDeTaijutsu.class, resultado); //é do tipo NinjaDeTaijutsu
    }

    @Test
    void deveCriarNinjaNinQuandoDadosValidos() {
        PersonagemRequest req = new PersonagemRequest("Sasuke", 100, "Ninjutsu");
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Sasuke", 100);

        when(personagemRepository.save(any(Personagem.class)))
                .thenReturn(ninja);

        Personagem resultado = personagemService.criarPersonagem(req);

        assertNotNull(resultado);
        assertEquals("Sasuke", resultado.getNome());
        assertInstanceOf(NinjaDeNinjutsu.class, resultado); //é do tipo NinjaDeNinjutsu
    }

    @Test
    void deveLancararExcecaoQuandoTipoNinjaInvalido() {
        PersonagemRequest req = new PersonagemRequest("Sakura", 100, "Genjutsu");

        assertThrows(RuntimeException.class,
                () -> personagemService.criarPersonagem(req));
    }

    @Test
    void deveLancararExcecaoQuandoNinjaNulo() {
        PersonagemRequest req = new PersonagemRequest("Kakashi", 100, "");

        assertThrows(RuntimeException.class,
                () -> personagemService.criarPersonagem(req));
    }

    @Test
    void deveAdicionarJutsuQuandoDadosValidos() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Naruto", 100);
        ninja.setId(1);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(ninja));

        personagemService.adicionarJutsu(1,
                new JutsuRequest("Rasengan", 30, 20));

        assertEquals(1, ninja.getJutsus().size());
        assertTrue(ninja.getJutsus().containsKey("rasengan"));
    }

    @Test
    void deveLancararExcecaoQuandoAdicionarJutsuDuplicado() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Sasuke", 100);
        ninja.setId(1);
        ninja.getJutsus().put("chidori", null);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(ninja));

        assertThrows(RuntimeException.class,
                () -> personagemService.adicionarJutsu(1,
                        new JutsuRequest("Chidori", 40, 30)));
    }

    @Test
    void deveBuscarPersonagemPorIdQuandoExistente() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu("Shikamaru", 100);
        ninja.setId(1);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(ninja));

        Personagem resultado = personagemService.buscarPersonagemPorId(1);

        assertNotNull(resultado);
        assertEquals("Shikamaru", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarPersonagemInexistente() {
        when(personagemRepository.findById(2))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> personagemService.buscarPersonagemPorId(2));
    }

    @Test
    void deveDeletarPersonagemQuandoExistente() {
        NinjaDeTaijutsu ninja = new NinjaDeTaijutsu("Neji", 100);
        ninja.setId(1);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(ninja));

        personagemService.deletarPersonagem(1);

        verify(personagemRepository).delete(ninja);
    }

    @Test
    void deveLancarExcecaoQuandoDeletarPersonagemInexistente() {
        when(personagemRepository.findById(2))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> personagemService.deletarPersonagem(2));
    }
}
