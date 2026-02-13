package com.db.naruto_II.unit;

import com.db.naruto_II.dto.AtaqueRequest;
import com.db.naruto_II.service.CombateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.db.naruto_II.controller.CombateController;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CombateControllerTest {

    @InjectMocks
    private CombateController controller;

    @Mock
    private CombateService service;

    @Test
    void deveChamarServiceAoAtacar() {

        var request = new AtaqueRequest(1, 2, "Chidori");

        controller.atacarComJutsu(request);

        verify(service).atacarComJutsu(1, 2, "Chidori");
    }
}

