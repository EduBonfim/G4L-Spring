package com.jogosparavida.games4life_backend.dto;

import java.util.List;

public record RentalCreateDto(
        Long clientId,
        Long consoleId,
        String plan,
        List<Long> gamesIds,
        List<Long> accessoriesIds,
        Boolean purchaseOption
) {}