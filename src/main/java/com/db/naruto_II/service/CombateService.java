package com.db.naruto_II.service;

import com.db.naruto_II.entity.Jutsu;
import com.db.naruto_II.entity.Personagem;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CombateService {

    private final PersonagemService personagemService;

    public Jutsu encontrarJutsuPeloNome(Integer id, String nomeJutsu) {
        Personagem personagem = personagemService.buscarPersonagemPorId(id);

        if (personagem.getJutsus().isEmpty()){
            throw new RuntimeException("O personagem não possui jutsus");
        }

        Jutsu jutsu = personagem.getJutsus().get(nomeJutsu.toLowerCase());

        if (jutsu == null) {
            throw new RuntimeException("Jutsu não encontrado");
        }

        return jutsu;
    }

    public Personagem validarPersonagemVivo(Integer id){
        Personagem personagem = personagemService.buscarPersonagemPorId(id);

        if (!personagem.estaVivo()){
            throw new RuntimeException("O personagem já está morto");
        }

        return personagem;
    }

    @Transactional
    public void atacarComJutsu(Integer idAtacante, Integer idDefensor, String nomeJutsu) {

        Personagem atacante = validarPersonagemVivo(idAtacante);

        Jutsu jutsu = encontrarJutsuPeloNome(idAtacante, nomeJutsu);

        atacante.usarJutsu(jutsu);

        Personagem defensor = validarPersonagemVivo(idDefensor);

        boolean desviou = defensor.desviar();

        if (!desviou){
            defensor.receberDano(jutsu.getDano());
        }
    }
}
