package com.jogosparavida.games4life_backend.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jogosparavida.games4life_backend.service.ConsoleService;
import com.jogosparavida.games4life_backend.dto.ConsoleDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/consoles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ConsoleController {

	
	private final ConsoleService consoleService;
	
	@GetMapping
    public ResponseEntity<List<ConsoleDto>> getAllConsoles() {
        List<ConsoleDto> consoles = consoleService.getAllConsoles();
        return ResponseEntity.ok(consoles);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ConsoleDto> getConsoleById(@PathVariable Long id) {
        ConsoleDto console = consoleService.getConsole(id);
        return ResponseEntity.ok(console);
    }


    @PostMapping
    public ResponseEntity<ConsoleDto> createConsole(@RequestBody ConsoleDto dto) {
        ConsoleDto created = consoleService.createConsole(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ConsoleDto> updateConsole(@PathVariable Long id, @RequestBody ConsoleDto dto) {
        ConsoleDto updated = consoleService.updateConsole(id, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsole(@PathVariable Long id) {
        consoleService.deleteConsole(id);
        return ResponseEntity.noContent().build();
    }
}
