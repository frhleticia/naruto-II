package com.db.naruto_II.controller;

import com.db.naruto_II.dto.JutsuRequest;
import com.db.naruto_II.dto.PersonagemRequest;
import com.db.naruto_II.entity.Jutsu;
import com.db.naruto_II.entity.Personagem;
import com.db.naruto_II.service.PersonagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personagens")
@RequiredArgsConstructor
public class PersonagemController {

    private final PersonagemService personagemService;

    @PostMapping
    public ResponseEntity<Personagem> criarPersonagem(@RequestBody PersonagemRequest personagemRequest) {
        Personagem personagem = personagemService.criarPersonagem(personagemRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personagem);
    }

    @PostMapping("/{id}/jutsus")
    public ResponseEntity<Jutsu> adicionarJutsu(@PathVariable Integer id, @RequestBody JutsuRequest jutsuRequest){
        Jutsu jutsu = personagemService.adicionarJutsu(id, jutsuRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jutsu);
    }

    @PostMapping("/{id}/chakra")
    public ResponseEntity<Personagem> aumentarChakra(@PathVariable Integer id, @RequestParam int quantidadeChakra) {
        Personagem personagem = personagemService.aumentarChakra(id, quantidadeChakra);

        return ResponseEntity.ok(personagem);
    }

    @GetMapping("/{id}")
    public Personagem buscarPersonagemPorId(@PathVariable Integer id) {
        return personagemService.buscarPersonagemPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPersonagem(@PathVariable  Integer id) {
        personagemService.deletarPersonagem(id);

        return ResponseEntity.noContent().build();
    }
}
