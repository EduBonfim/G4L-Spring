package com.jogosparavida.games4life_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jogosparavida.games4life_backend.service.AuthService;
import com.jogosparavida.games4life_backend.dto.RegisterDto;
import com.jogosparavida.games4life_backend.dto.LoginDto;
import com.jogosparavida.games4life_backend.dto.PasswordChangeDTO;

import com.jogosparavida.games4life_backend.dto.ClientDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") 
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ClientDto> register(@RequestBody RegisterDto dto) {
        var client = authService.register(dto);
        return ResponseEntity.status(201).body(client);
    }

    @PostMapping("/login")
    public ResponseEntity<ClientDto> login(@RequestBody LoginDto dto) {
        var client = authService.login(dto);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeDTO dto) {
        authService.changePassword(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<ClientDto> getCurrentUser() {
        // Por enquanto, retorna 401 pois não temos sessão HTTP implementada
        // TODO: Implementar gerenciamento de sessão com HttpSession ou JWT
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Por enquanto, apenas retorna sucesso
        // TODO: Implementar limpeza de sessão quando houver gerenciamento de sessão
        return ResponseEntity.noContent().build();
    }
}

