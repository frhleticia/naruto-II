package com.db.naruto_II.entity;

public class NinjaDeNinjutsu extends Personagem implements Ninja {
    public NinjaDeNinjutsu(String nome, int vida) {
        super(nome, vida);
    }

    @Override
    public void usarJutsu(Jutsu jutsu) {

        if (getChakra() < jutsu.getConsumoDeChakra()){
            throw new RuntimeException("O personagem atacante não tem chakra suficiente para usar esse jutsu");
        }

        gastarChakra(jutsu.getConsumoDeChakra());

        System.out.println(getNome()+" está usando um jutsu usando sua habilidade em Ninjutsu.");
    }

    @Override
    public boolean desviar() {

        int chance = 40;
        int sorte = (int) (Math.random() * 100);

        System.out.println(getNome()+" está desviando de um ataque usando sua habilidade em Ninjutsu.");

        return sorte < chance;
    }
}
