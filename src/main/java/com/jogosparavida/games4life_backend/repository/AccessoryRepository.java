package com.jogosparavida.games4life_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jogosparavida.games4life_backend.model.Accessory;

public interface AccessoryRepository  extends JpaRepository<Accessory, Long>{
	boolean existsByNameIgnoreCase(String name);

}
