package com.db.naruto_II.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "personagem")

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_ninja")

@Entity
public abstract class Personagem implements Ninja {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @ElementCollection
    @MapKeyColumn(name = "nome_jutsu")
    @CollectionTable(name = "personagem_jutsu", joinColumns = @JoinColumn(name="personagem_id"))
    private Map<String, Jutsu> jutsus = new HashMap<>();

    @Column(name = "chakra")
    private int chakra = 100;

    @Column(name = "vida")
    private int vida;

    public Personagem(String nome, int vida) {
        this.nome = nome;
        this.vida = vida;
    }

    public void receberDano(int dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }

    public void gastarChakra(int custo) {
        if (this.chakra < custo) {
            throw new RuntimeException("Chakra insuficiente");
        }
        this.chakra -= custo;
    }

    public boolean estaVivo() {
        return getVida() > 0;
    }
}
