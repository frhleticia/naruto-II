package com.db.naruto_II.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@DiscriminatorValue("TAIJUTSU")
@Entity
public class NinjaDeTaijutsu extends Personagem {

    public NinjaDeTaijutsu(String nome, int vida) {
        super(nome, vida);
    }

    @Override
    public void usarJutsu(Jutsu jutsu) {
        gastarChakra(jutsu.getConsumoDeChakra());

        System.out.println(getNome()+" está usando um jutsu usando sua habilidade em Taijutsu.");
    }

    @Override
    public boolean desviar() {
        System.out.println(getNome()+" está desviando de um ataque usando sua habilidade em Taijutsu.");

        return gerarChance() < 0.60;
    }
}
