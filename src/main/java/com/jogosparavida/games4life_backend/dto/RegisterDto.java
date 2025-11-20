package com.jogosparavida.games4life_backend.dto;

public record RegisterDto(
	    String name,
	    String cpf,
	    String email,
	    String phone,
	    String username,
	    String password
	) {}
