package com.jogosparavida.games4life_backend.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.jogosparavida.games4life_backend.model.Console;

public interface ConsoleRepository extends JpaRepository<Console, Long> {
	boolean existsByNameIgnoreCase(String name);
}
