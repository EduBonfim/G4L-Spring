package com.jogosparavida.games4life_backend.mapper;

import com.jogosparavida.games4life_backend.dto.ConsoleDto;
import com.jogosparavida.games4life_backend.model.Console;

public class ConsoleMapper {

	public ConsoleDto todto(Console c) {
		return new ConsoleDto(c.getId(), c.getName(), c.getPrice(), c.getImagem());

	}

	public Console toEntity(ConsoleDto dto) {
        Console console = new Console();
        console.setName(dto.name());
        console.setPrice(dto.price());
        console.setImagem(dto.image());
        return console;
    }
	
}
