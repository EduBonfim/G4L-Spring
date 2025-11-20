package com.jogosparavida.games4life_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jogosparavida.games4life_backend.dto.AddressDto;
import com.jogosparavida.games4life_backend.dto.ClientDto;
import com.jogosparavida.games4life_backend.dto.ClientUpdateDto;
import com.jogosparavida.games4life_backend.dto.ClientWithAddressesDto;
import com.jogosparavida.games4life_backend.mapper.AddressMapper;
import com.jogosparavida.games4life_backend.mapper.ClientMapper;
import com.jogosparavida.games4life_backend.model.Address;
import com.jogosparavida.games4life_backend.model.Client;
import com.jogosparavida.games4life_backend.repository.ClientRepository;
import com.jogosparavida.games4life_backend.repository.RentalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
        private final ClientMapper clientMapper = new ClientMapper();
    private final AddressMapper addressMapper = new AddressMapper();
        private final RentalRepository rentalRepository;

    // LISTAR TODOS
    @Transactional(readOnly = true)
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    // GET CLIENTE SEM ENDEREÇOS
    @Transactional(readOnly = true)
        @SuppressWarnings("null")
        public ClientDto getClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return clientMapper.toDto(client);
    }

    // GET CLIENTE + ENDEREÇOS
    @Transactional(readOnly = true)
        @SuppressWarnings("null")
        public ClientWithAddressesDto getClientWithAddresses(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<AddressDto> addresses = client.getAddresses()
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());

        return new ClientWithAddressesDto(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getUsername(),
                client.getEmail(),
                client.getPhone(),
                addresses
        );
    }


    @Transactional
        @SuppressWarnings("null")
        public ClientDto createClient(ClientDto dto) {

        Client client = clientMapper.toEntity(dto);

        Client saved = clientRepository.save(client);
        return clientMapper.toDto(saved);
    }


    @Transactional
        @SuppressWarnings("null")
        public ClientDto updateClient(Long id, ClientUpdateDto dto) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        client.setName(dto.name());
        client.setEmail(dto.email());
        client.setPhone(dto.phone());

        Client updated = clientRepository.save(client);
        return clientMapper.toDto(updated);
    }

    @Transactional
        @SuppressWarnings("null")
        public void deleteClient(Long id) {
                Client c = clientRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

                // Excluir aluguéis do cliente antes de remover o cliente
                var rentals = rentalRepository.findByClient_Id(id);
                if (!rentals.isEmpty()) {
                        rentalRepository.deleteAll(rentals);
                }

                clientRepository.delete(c);
        }
    
    @Transactional
        @SuppressWarnings("null")
        public ClientWithAddressesDto updateClientAddresses(Long clientId, List<AddressDto> dtos) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));


        client.getAddresses().clear();


        List<Address> novas = dtos.stream()
                .map(addressMapper::toEntity)
                .peek(a -> a.setClient(client))
                .collect(Collectors.toList());

        client.setAddresses(novas);

        Client saved = clientRepository.save(client);


        List<AddressDto> addresses = saved.getAddresses()
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());

        return new ClientWithAddressesDto(
                saved.getId(),
                saved.getName(),
                saved.getCpf(),
                saved.getEmail(),
                saved.getPhone(),
                saved.getUsername(),
                addresses
        );
    }

}
