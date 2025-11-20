package com.jogosparavida.games4life_backend.dto;

import java.math.BigDecimal;

public record ConsoleDto(
		Long id,
		String name,
		BigDecimal price,
		String image) {

}
