package com.db.naruto_II.controller;

import com.db.naruto_II.dto.PersonagemRequest;
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

    @GetMapping
    public void buscarPersonagemPorId(@RequestParam Integer id) {
        personagemService.buscarPersonagemPorId(id);
    }

    @DeleteMapping
    public void deletarPersonagem(@RequestParam Integer id) {
        personagemService.deletarPersonagem(id);
    }
}
