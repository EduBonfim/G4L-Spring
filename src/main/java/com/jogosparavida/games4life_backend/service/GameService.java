package com.jogosparavida.games4life_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jogosparavida.games4life_backend.dto.GameDto;
import com.jogosparavida.games4life_backend.mapper.GameMapper;
import com.jogosparavida.games4life_backend.model.Game;
import com.jogosparavida.games4life_backend.repository.GameRepository;
import com.jogosparavida.games4life_backend.repository.RentalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final RentalRepository rentalRepository;

    @Transactional(readOnly = true)
    public List<GameDto> getAllGames() {
        return gameRepository.findAll()
                             .stream()
                             .map(gameMapper::toDto)
                             .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public GameDto getGame(Long id) {
        Game game = gameRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
        return gameMapper.toDto(game);
    }

    @Transactional
    @SuppressWarnings("null")
    public GameDto createGame(GameDto dto) {
        Game game = gameMapper.toEntity(dto);
        Game saved = gameRepository.save(game);
        return gameMapper.toDto(saved);
    }

    @Transactional
    @SuppressWarnings("null")
    public GameDto updateGame(Long id, GameDto dto) {
        Game game = gameRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));

        game.setName(dto.name());
        game.setPrice(dto.price());
        game.setImagem(dto.image());
        game.setConsole(dto.console());

        Game updated = gameRepository.save(game);
        return gameMapper.toDto(updated);
    }

    @Transactional
    @SuppressWarnings("null")
    public void deleteGame(Long id) {
        Game game = gameRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));

        // Remover relacionamento com aluguéis antes de excluir
        rentalRepository.findByGames_Id(id).forEach(r -> {
            r.getGames().removeIf(g -> g.getId().equals(id));
            rentalRepository.save(r);
        });

        gameRepository.delete(game);
    }
}
