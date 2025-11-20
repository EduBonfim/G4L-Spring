package com.jogosparavida.games4life_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jogosparavida.games4life_backend.dto.AddressDto;
import com.jogosparavida.games4life_backend.service.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AddressDto>> listByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(addressService.getAddressesByClient(clientId));
    }

    @PostMapping("/client/{clientId}")
    public ResponseEntity<AddressDto> create(@PathVariable Long clientId, @RequestBody AddressDto dto) {
        return ResponseEntity.ok(addressService.createAddress(clientId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> update(@PathVariable Long id, @RequestBody AddressDto dto) {
        return ResponseEntity.ok(addressService.updateAddress(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
