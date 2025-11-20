package com.jogosparavida.games4life_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jogosparavida.games4life_backend.dto.ConsoleDto;
import com.jogosparavida.games4life_backend.mapper.ConsoleMapper;
import com.jogosparavida.games4life_backend.model.Console;
import com.jogosparavida.games4life_backend.repository.ConsoleRepository;
import com.jogosparavida.games4life_backend.repository.RentalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsoleService {

    private final ConsoleRepository consoleRepository;
    private final ConsoleMapper consoleMapper = new ConsoleMapper();
    private final RentalRepository rentalRepository;

    @Transactional(readOnly = true)
    public List<ConsoleDto> getAllConsoles() {
        return consoleRepository.findAll()
                                .stream()
                                .map(consoleMapper::todto)
                                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public ConsoleDto getConsole(Long id) {
        Console console = consoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Console não encontrado"));
        return consoleMapper.todto(console);
    }

    @Transactional
    @SuppressWarnings("null")
    public ConsoleDto createConsole(ConsoleDto dto) {
        Console console = consoleMapper.toEntity(dto);
        Console saved = consoleRepository.save(console);
        return consoleMapper.todto(saved);
    }

    @Transactional
    @SuppressWarnings("null")
    public ConsoleDto updateConsole(Long id, ConsoleDto dto) {
        Console console = consoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Console não encontrado"));

        console.setName(dto.name());
        console.setPrice(dto.price());
        console.setImagem(dto.image());

        Console updated = consoleRepository.save(console);
        return consoleMapper.todto(updated);
    }

    @Transactional
    @SuppressWarnings("null")
    public void deleteConsole(Long id) {
        Console console = consoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Console não encontrado"));

        // Excluir aluguéis que referenciam este console pois não é possível null porque optional=false
        var rentals = rentalRepository.findByConsole_Id(id);
        if (!rentals.isEmpty()) {
            rentalRepository.deleteAll(rentals);
        }

        consoleRepository.delete(console);
    }
}
