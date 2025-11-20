package com.jogosparavida.games4life_backend.mapper;

import org.springframework.stereotype.Component;

import com.jogosparavida.games4life_backend.dto.AddressDto;
import com.jogosparavida.games4life_backend.model.Address;
@Component
public class AddressMapper {

	public AddressDto toDto(Address a) {
        return new AddressDto(
                a.getId(),
                a.getAddress(),
                a.getNumber(),
                a.getDistrict(),
                a.getCep(),
                a.getCity(),
                a.getUf(),
                a.getReference(),
                a.getType(),
                a.getIsDefault()
        );
    }
	public Address toEntity(AddressDto dto) {
        Address a = new Address();
        a.setId(dto.id()); 
        a.setAddress(dto.address());
        a.setNumber(dto.number());
        a.setDistrict(dto.district());
        a.setCep(dto.cep());
        a.setCity(dto.city());
        a.setUf(dto.uf());
        a.setReference(dto.reference());
        a.setType(dto.type());
        a.setIsDefault(dto.isDefault());
        return a;
    }
}
