package com.db.naruto_II.repository;

import com.db.naruto_II.entity.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<Personagem, Integer> {
}
