package com.jogosparavida.games4life_backend.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jogosparavida.games4life_backend.dto.AddressDto;
import com.jogosparavida.games4life_backend.dto.ClientDto;
import com.jogosparavida.games4life_backend.dto.ClientUpdateDto;
import com.jogosparavida.games4life_backend.dto.ClientWithAddressesDto;
import com.jogosparavida.games4life_backend.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = {
    "http://localhost:4200",
    "https://g4-l-angular.vercel.app",
    "https://g4l-spring.onrender.com"
})
public class ClientController {

    private final ClientService clientService;


    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }


    @GetMapping("/{id}/addresses")
    public ResponseEntity<ClientWithAddressesDto> getClientWithAddresses(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientWithAddresses(id));
    }


    @PostMapping
    @SuppressWarnings("null")
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto dto) {
        ClientDto created = clientService.createClient(dto);
        return ResponseEntity
                .created(URI.create("/api/clients/" + created.id()))
                .body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(
            @PathVariable Long id,
            @RequestBody ClientUpdateDto dto) {

        return ResponseEntity.ok(clientService.updateClient(id, dto));
    }


    @PutMapping("/{id}/addresses")
    public ResponseEntity<ClientWithAddressesDto> updateClientAddresses(
            @PathVariable Long id,
            @RequestBody List<AddressDto> addresses) {

        return ResponseEntity.ok(clientService.updateClientAddresses(id, addresses));
    }

 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
