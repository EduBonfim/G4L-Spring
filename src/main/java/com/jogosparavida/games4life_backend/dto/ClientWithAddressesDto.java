package com.jogosparavida.games4life_backend.dto;

import java.util.List;


	public record ClientWithAddressesDto(
		    Long id,
		    String name,
		    String cpf,
		    String email,
		    String phone,
		    String username,
		    List<AddressDto> address
		) {}

