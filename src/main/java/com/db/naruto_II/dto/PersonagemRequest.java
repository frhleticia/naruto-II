package com.db.naruto_II.dto;

import com.db.naruto_II.entity.Jutsu;

import java.util.Map;

public record PersonagemRequest(String nome, int chakra, int vida) {
}
