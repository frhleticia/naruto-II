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
@Entity
public class Personagem {

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

    public Personagem(String nome, int chakra, int vida) {
        this.nome = nome;
        this.chakra = chakra;
        this.vida = vida;
    }

    public void receberDano(int dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }

    public void gastarChakra(int custo) {
        this.chakra -= custo;
        if (this.chakra < 0) {
            this.chakra = 0;
        }
    }

}
