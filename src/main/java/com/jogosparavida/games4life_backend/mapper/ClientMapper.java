package com.jogosparavida.games4life_backend.mapper;

import com.jogosparavida.games4life_backend.dto.ClientDto;
import com.jogosparavida.games4life_backend.model.Client;
import java.util.stream.Collectors;

public class ClientMapper {

    private final AddressMapper addressMapper = new AddressMapper();

    public ClientDto toDto(Client c) {
        return new ClientDto(
                c.getId(),
                c.getName(),
                c.getCpf(),
                c.getUsername(),
                c.getEmail(),
                c.getPhone(),
                c.getAddresses().stream()
                    .map(addressMapper::toDto)
                    .collect(Collectors.toList())
        );
    }

    public Client toEntity(ClientDto dto) {
        Client c = new Client();
        c.setId(dto.id());
        c.setName(dto.name());
        c.setCpf(dto.cpf());
        c.setUsername(dto.username());
        c.setEmail(dto.email());
        c.setPhone(dto.phone());
        return c;
    }
}
