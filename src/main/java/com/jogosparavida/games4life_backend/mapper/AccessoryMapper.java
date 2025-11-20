package com.jogosparavida.games4life_backend.mapper;

import com.jogosparavida.games4life_backend.dto.AccessoryDto;
import com.jogosparavida.games4life_backend.model.Accessory;

public class AccessoryMapper {

	public AccessoryDto toDto(Accessory a) {
		return new AccessoryDto(
				a.getId(),
				a.getName(),
				a.getPrice(),
				a.getImagem(),
				a.getConsole());
	}
	public Accessory toEntity(AccessoryDto dto) {
        Accessory a = new Accessory();
        a.setName(dto.name());
        a.setPrice(dto.price());
        a.setImagem(dto.image());
        a.setConsole(dto.console());
        return a;
    }
}
