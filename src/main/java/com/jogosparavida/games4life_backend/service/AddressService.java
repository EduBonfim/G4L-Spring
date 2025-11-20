package com.jogosparavida.games4life_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jogosparavida.games4life_backend.dto.AddressDto;
import com.jogosparavida.games4life_backend.mapper.AddressMapper;
import com.jogosparavida.games4life_backend.model.Address;
import com.jogosparavida.games4life_backend.model.Client;
import com.jogosparavida.games4life_backend.repository.AddressRepository;
import com.jogosparavida.games4life_backend.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final AddressMapper addressMapper;

    @Transactional(readOnly = true)
    public List<AddressDto> getAddressesByClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return client.getAddresses()
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }


    @Transactional
    public AddressDto createAddress(Long clientId, AddressDto dto) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Address address = addressMapper.toEntity(dto);
        address.setClient(client);

        
        if (Boolean.TRUE.equals(dto.isDefault())) {
            client.getAddresses().forEach(a -> a.setIsDefault(false));
        }

        Address saved = addressRepository.save(address);
        return addressMapper.toDto(saved);
    }

    @Transactional
    public AddressDto updateAddress(Long addressId, AddressDto dto) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        address.setAddress(dto.address());
        address.setNumber(dto.number());
        address.setDistrict(dto.district());
        address.setCep(dto.cep());
        address.setCity(dto.city());
        address.setUf(dto.uf());
        address.setReference(dto.reference());
        address.setType(dto.type());


        if (Boolean.TRUE.equals(dto.isDefault())) {
            Long clientId = address.getClient().getId();
            addressRepository.findByClientId(clientId)
                    .forEach(a -> a.setIsDefault(false));
            address.setIsDefault(true);
        }

        Address updated = addressRepository.save(address);
        return addressMapper.toDto(updated);
    }


    @Transactional
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        addressRepository.delete(address);
    }
}
