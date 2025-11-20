package com.jogosparavida.games4life_backend.dto;

import java.util.List;

public record ClientDto(
        Long id,
        String name,
        String cpf,
        String username,
        String email,
        String phone,
        List<AddressDto> addresses
) {}
