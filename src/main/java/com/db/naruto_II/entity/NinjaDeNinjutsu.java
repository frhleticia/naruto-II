package com.db.naruto_II.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@DiscriminatorValue("NINJUTSU")
@Entity
public class NinjaDeNinjutsu extends Personagem {

    public NinjaDeNinjutsu(String nome, int vida) {
        super(nome, vida);
    }

    @Override
    public void usarJutsu(Jutsu jutsu) {
        gastarChakra(jutsu.getConsumoDeChakra());

        System.out.println(getNome()+" está usando um jutsu usando sua habilidade em Ninjutsu.");
    }

    @Override
    public boolean desviar() {
        System.out.println(getNome()+" está desviando de um ataque usando sua habilidade em Ninjutsu.");

        return gerarChance() < 0.40;
    }
}
