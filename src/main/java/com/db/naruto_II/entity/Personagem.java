package com.db.naruto_II.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    private Map<String, Jutsu> jutsus = new HashMap<>();

    private int chakra = 100;

    private int vida;

    public Personagem(String nome, Map<String, Jutsu> jutsus, int chakra, int vida) {
        this.nome = nome;
        this.jutsus = jutsus;
        this.chakra = chakra;
        this.vida = vida;
    }

}
