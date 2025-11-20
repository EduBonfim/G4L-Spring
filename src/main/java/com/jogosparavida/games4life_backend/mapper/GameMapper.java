package com.jogosparavida.games4life_backend.mapper;

import org.springframework.stereotype.Component;
import com.jogosparavida.games4life_backend.dto.GameDto;
import com.jogosparavida.games4life_backend.model.Game;

@Component
public class GameMapper {

    public GameDto toDto(Game g) {
        return new GameDto(
            g.getId(),
            g.getName(),
            g.getPrice(),
            g.getImagem(),
            g.getConsole() 
        );
    }

    public Game toEntity(GameDto dto) {
        Game game = new Game();
        game.setId(dto.id());
        game.setName(dto.name());
        game.setPrice(dto.price());
        game.setImagem(dto.image());
        game.setConsole(dto.console());
        return game;
    }
}
