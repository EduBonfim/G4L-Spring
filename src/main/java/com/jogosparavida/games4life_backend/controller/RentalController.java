package com.jogosparavida.games4life_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jogosparavida.games4life_backend.dto.RentalCreateDto;
import com.jogosparavida.games4life_backend.dto.RentalDto;
import com.jogosparavida.games4life_backend.service.RentalService;
import com.jogosparavida.games4life_backend.dto.RentalExtendDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RentalController {

    private final RentalService rentalService;

     @GetMapping
    public ResponseEntity<List<RentalDto>> getAll() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/me")
    public ResponseEntity<List<RentalDto>> getForCurrentUser(
            @RequestHeader(name = "X-Client-Cpf", required = false) String cpf,
            @RequestParam(name = "cpf", required = false) String cpfParam) {
        String finalCpf = (cpf != null && !cpf.isBlank()) ? cpf : cpfParam;
        if (finalCpf == null || finalCpf.isBlank()) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(rentalService.getRentalsByClientCpf(finalCpf));
    }
    // Restringe o {id} para apenas dígitos, evitando conflito com "/me"
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<RentalDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRental(id));
    }

    @PostMapping
    public ResponseEntity<RentalDto> create(@RequestBody RentalCreateDto dto) {
        return ResponseEntity.ok(rentalService.createRental(dto));
    }
    @PostMapping("/{id}/extend")
    public ResponseEntity<RentalDto> extendRental(
            @PathVariable Long id, 
            @RequestBody RentalExtendDto dto) {
        
        // O Controller apenas passa o trabalho para o Service
        return ResponseEntity.ok(rentalService.extendRental(id, dto));
    }
    @PostMapping("/{id}/confirm-payment")
    public ResponseEntity<RentalDto> confirmPayment(@PathVariable Long id) {
        // Apenas passa o trabalho para o Service
        return ResponseEntity.ok(rentalService.confirmPayment(id));
    }
}
