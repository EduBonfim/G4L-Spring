package com.jogosparavida.games4life_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jogosparavida.games4life_backend.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {

	// Permite buscar aluguéis por CPF do cliente (usado pelo endpoint /api/rentals/me)
	java.util.List<Rental> findByClientCpf(String cpf);

	// Para operações de exclusão em cascata/limpeza de relacionamentos
	java.util.List<Rental> findByAcessories_Id(Long accessoryId);
	java.util.List<Rental> findByGames_Id(Long gameId);
	java.util.List<Rental> findByConsole_Id(Long consoleId);
	java.util.List<Rental> findByClient_Id(Long clientId);

}
