package com.db.naruto_II.service;

import com.db.naruto_II.entity.Jutsu;
import com.db.naruto_II.entity.Personagem;
import org.springframework.stereotype.Service;

@Service
public class CombateService {

    private final PersonagemService personagemService;

    public CombateService(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    public Personagem buscarPersonagemVivo(Integer id){
        Personagem personagem = personagemService.buscarPersonagemPorId(id);

        if (!personagem.estaVivo()){
            throw new RuntimeException("O personagem já está morto");
        }

        return personagem;
    }

    public Jutsu encontrarJutsuPeloNome(Integer id, String nomeJutsu) {

        Personagem personagem = personagemService.buscarPersonagemPorId(id);

        if (personagem.getJutsus().isEmpty()){
            throw new RuntimeException("O personagem não possui jutsus");
        }

        return personagem.getJutsus().get(nomeJutsu);
    }

    public void atacarComJutsu(Integer idAtacante, Integer idDefensor, String nomeJutsu) {

        Personagem atacante = buscarPersonagemVivo(idAtacante);
        Jutsu jutsu = encontrarJutsuPeloNome(idAtacante, nomeJutsu);

        atacante.usarJutsu(jutsu);

        Personagem defensor = buscarPersonagemVivo(idDefensor);
        boolean desviou = defensor.desviar();

        if (!desviou){
            defensor.receberDano(jutsu.getDano());
        }
    }
}
