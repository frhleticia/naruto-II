package com.db.naruto_II.service;

import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.Jutsu;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.repository.PersonagemRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonagemService {

    private PersonagemRepository personagemRepository;

    public PersonagemService(PersonagemRepository personagemRepository) {
        this.personagemRepository = personagemRepository;
    }

    public void criarPersonagem(PersonagemRequest request) {
        Personagem personagem = new Personagem(
                request.nome(), request.chakra(), request.vida()
        );

        personagemRepository.saveAndFlush(personagem);
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

        String nome = jutsuRequest.nome();

        if (nome == null || nome.isEmpty()) {
            throw new RuntimeException("Nome do jutsu não pode ser nulo ou vazio");
        }

        Jutsu jutsu = new Jutsu(jutsuRequest.dano(), jutsuRequest.consumoDeChakra());

        personagem.getJutsus().putIfAbsent(nome, jutsu);

        personagemRepository.saveAndFlush(personagem);
    }
}
