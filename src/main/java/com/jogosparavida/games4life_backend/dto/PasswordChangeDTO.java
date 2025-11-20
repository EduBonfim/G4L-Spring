package com.jogosparavida.games4life_backend.dto;

public record PasswordChangeDTO(
		String cpf,
		String currentPassword,
		String newPassword) {

}
