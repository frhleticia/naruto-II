package com.db.naruto_II.entity;

public class NinjaDeTaijutsu extends Personagem implements Ninja {
    public NinjaDeTaijutsu(String nome, int chakra, int vida) {
        super(nome, chakra, vida);
    }

    @Override
    public void usarJutsu(Personagem adversario) {

        if (getChakra()<10){
            throw new RuntimeException("O personagem não possui chakra suficiente para usar um jutsu.");
        }

        if (getJutsus().isEmpty()){
            throw new RuntimeException("O personagem não possui jutsus para usar.");
        }

        Jutsu jutsu = getJutsus().values().iterator().next();
        setChakra(getChakra()-10);
        System.out.println(getNome()+" está usando um jutsu usando sua habilidade em Taijutsu.");
    }

    @Override
    public void desviar(Jutsu jutsu) {
        System.out.println(getNome()+" está desviando de um ataque usando sua habilidade em Taijutsu.");
        if (getChakra()>10) {
            System.out.println(getNome()+" conseguiu desviar do ataque.");
        } else {
            System.out.println(getNome()+" não conseguiu desviar do ataque.");
            setVida(getVida()-jutsu.getDano());
        }
    }
}
