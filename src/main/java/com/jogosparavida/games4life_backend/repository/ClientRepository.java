package com.jogosparavida.games4life_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jogosparavida.games4life_backend.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	Optional<Client> findByCpf(String cpf);

	Optional<Client> findByUsername(String username);

	boolean existsByCpf(String cpf);

	boolean existsByUsernameIgnoreCase(String username);
}
