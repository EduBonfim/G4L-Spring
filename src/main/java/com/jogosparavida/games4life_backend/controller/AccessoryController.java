package com.jogosparavida.games4life_backend.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jogosparavida.games4life_backend.service.AccessoryService;
import com.jogosparavida.games4life_backend.dto.AccessoryDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accessories")
@RequiredArgsConstructor
@CrossOrigin(origins = {
    "http://localhost:4200",
    "https://g4-l-angular.vercel.app",
    "https://g4l-spring.onrender.com"
})
public class AccessoryController {

    private final AccessoryService accessoryService;


    @GetMapping
    public ResponseEntity<List<AccessoryDto>> getAllAccessories() {
        return ResponseEntity.ok(accessoryService.getAllAccessories());
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccessoryDto> getAccessory(@PathVariable Long id) {
        return ResponseEntity.ok(accessoryService.getAccessory(id));
    }


    @PostMapping
    public ResponseEntity<AccessoryDto> createAccessory(@RequestBody AccessoryDto dto) {
        return ResponseEntity.ok(accessoryService.createAccessory(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessoryDto> updateAccessory(@PathVariable Long id, @RequestBody AccessoryDto dto) {
        return ResponseEntity.ok(accessoryService.updateAccessory(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessory(@PathVariable Long id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }
}