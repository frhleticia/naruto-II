package com.db.naruto_II.controller;

import com.db.naruto_II.dto.AtaqueRequest;
import com.db.naruto_II.service.CombateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/combates")
@RequiredArgsConstructor
public class CombateController {

    private final CombateService combateService;

    @PostMapping("/atacar")
    public void atacarComJutsu(@RequestBody AtaqueRequest req) {
        combateService.atacarComJutsu(req.idAtacante(), req.idDefensor(), req.nomeJutsu());
    }
}
