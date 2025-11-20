package com.jogosparavida.games4life_backend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record RentalDto(
        Long id,
        String clientName,
        String clientCpf,
        String consoleName,
        String plan,
        List<String> games,
        List<String> acessories,
        Boolean purchaseOption,
        BigDecimal totalPrice,
        Instant orderDate,
        Instant endDate,
        Boolean paymentConfirmed,
        BigDecimal pendingExtensionAmount,
        String pendingExtensionStatus
) {}

