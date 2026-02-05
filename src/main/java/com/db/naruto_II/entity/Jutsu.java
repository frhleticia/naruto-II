package com.db.naruto_II.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Jutsu {

    private int dano;
    private int consumoDeChakra;

    public Jutsu(int dano, int consumoDeChakra) {
        this.dano = dano;
        this.consumoDeChakra = consumoDeChakra;
    }
}
