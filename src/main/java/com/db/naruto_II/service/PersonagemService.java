package com.db.naruto_II.service;

import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.Jutsu;
import com.db.naruto_II.entity.NinjaDeNinjutsu;
import com.db.naruto_II.entity.NinjaDeTaijutsu;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.repository.PersonagemRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonagemService {

    private final PersonagemRepository personagemRepository;

    public PersonagemService(PersonagemRepository personagemRepository) {
        this.personagemRepository = personagemRepository;
    }

    public void criarPersonagem(PersonagemRequest request) {
        Personagem personagem;

        if ("TAIJUTSU".equalsIgnoreCase(request.tipoNinja())) {
            personagem = new NinjaDeTaijutsu(request.nome(), request.vida());
        } else if ("NINJUTSU".equalsIgnoreCase(request.tipoNinja())) {
            personagem = new NinjaDeNinjutsu(request.nome(), request.vida());
        } else {
            throw new RuntimeException("Tipo de ninja inválido");
        }

        personagemRepository.save(personagem);
    }

    public Personagem buscarPersonagemPorId(Integer id) {
        return personagemRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Personagem não encontrado")
        );
    }

    public void deletarPersonagem(Integer id) {
        personagemRepository.deleteById(id);
    }

    public void adicionarJutsu(Integer id, JutsuRequest jutsuRequest){
        Personagem personagem = buscarPersonagemPorId(id);

        String nome = jutsuRequest.nome().trim().toLowerCase();

        if (personagem.getJutsus().containsKey(nome)) {
            throw new RuntimeException("Jutsu já existe para esse personagem");
        }

        Jutsu jutsu = new Jutsu(jutsuRequest.dano(), jutsuRequest.consumoDeChakra());

        personagem.getJutsus().put(nome, jutsu);

        personagemRepository.save(personagem);
    }

    public void aumentarChakra(Integer id, int quantidadeChakra) {
        if (quantidadeChakra <= 0) {
            throw new RuntimeException("Quantidade de chakra inválida");
        }

        Personagem personagem = buscarPersonagemPorId(id);
        personagem.setChakra(personagem.getChakra() + quantidadeChakra);

        personagemRepository.save(personagem);
    }
}

