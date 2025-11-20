package com.jogosparavida.games4life_backend.mapper;

import org.springframework.stereotype.Component;

import com.jogosparavida.games4life_backend.dto.RentalDto;
import com.jogosparavida.games4life_backend.model.Rental;

@Component
public class RentalMapper {

	public RentalDto toDto(Rental rental) {
		
		String clientName = "";
		String clientCpf = "";
		if (rental.getClient() != null) {
			clientName = rental.getClient().getName() != null ? rental.getClient().getName() : "";
			clientCpf = rental.getClient().getCpf() != null ? rental.getClient().getCpf() : "";
		}

		String consoleName = rental.getConsole() != null && rental.getConsole().getName() != null
				? rental.getConsole().getName()
				: "";

		java.util.List<String> games = rental.getGames() == null ? java.util.List.of() : rental.getGames().stream().map(g -> g.getName()).toList();
		java.util.List<String> accessories = rental.getAcessories() == null ? java.util.List.of() : rental.getAcessories().stream().map(a -> a.getName()).toList();

		// Converter java.util.Date para Instant de forma segura pois o java.sql.Date não suporta toInstant direto
		java.time.Instant endInstant = null;
		if (rental.getEndDate() != null) {
			// Usa Calendar para converter Date em millis e depois em Instant
			java.util.Calendar cal = java.util.Calendar.getInstance();
			cal.setTime(rental.getEndDate());
			endInstant = java.time.Instant.ofEpochMilli(cal.getTimeInMillis());
		}

		return new RentalDto(
			rental.getId(),
			clientName,
			clientCpf,
			consoleName,
			rental.getPlan(),
			games,
			accessories,
			rental.getPurchaseOption(),
			rental.getTotalPrice(),
			rental.getOrderDate(),
			endInstant,
			Boolean.valueOf(rental.isPaymentConfirmed()),
			rental.getPendingExtensionAmount() != null ? rental.getPendingExtensionAmount() : java.math.BigDecimal.ZERO,
			rental.getPendingExtensionStatus() != null ? rental.getPendingExtensionStatus() : "pago"
		);
	}

}
