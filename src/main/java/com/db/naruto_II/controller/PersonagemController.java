package com.db.naruto_II.controller;

import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.service.PersonagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personagens")
@RequiredArgsConstructor
public class PersonagemController {

    private final PersonagemService personagemService;

    @PostMapping
    public void criarPersonagem(@RequestBody PersonagemRequest personagemRequest) {
        personagemService.criarPersonagem(personagemRequest);
    }

    @PostMapping("/{id}/jutsus")
    public void adicionarJutsu(@PathVariable Integer id, @RequestBody JutsuRequest jutsuRequest){
        personagemService.adicionarJutsu(id, jutsuRequest);
    }

    @PostMapping("/{id}/chakra")
    public void aumentarChakra(@PathVariable Integer id, @RequestParam int quantidadeChakra) {
        personagemService.aumentarChakra(id, quantidadeChakra);
    }

    @GetMapping("/{id}")
    public Personagem buscarPersonagemPorId(@PathVariable Integer id) {
        return personagemService.buscarPersonagemPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarPersonagem(@PathVariable  Integer id) {
        personagemService.deletarPersonagem(id);
    }
}
