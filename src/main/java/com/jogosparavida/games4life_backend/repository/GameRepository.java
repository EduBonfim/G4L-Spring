package com.jogosparavida.games4life_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jogosparavida.games4life_backend.model.Game;

public interface GameRepository extends JpaRepository<Game, Long>{

	boolean  existsByNameIgnoreCase(String name);
}
