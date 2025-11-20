package com.jogosparavida.games4life_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jogosparavida.games4life_backend.dto.RegisterDto;
import com.jogosparavida.games4life_backend.dto.LoginDto;
import com.jogosparavida.games4life_backend.dto.PasswordChangeDTO;
import com.jogosparavida.games4life_backend.dto.ClientDto;
import com.jogosparavida.games4life_backend.model.Client;
import com.jogosparavida.games4life_backend.repository.ClientRepository;
import com.jogosparavida.games4life_backend.mapper.ClientMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper = new ClientMapper();
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Transactional
    public ClientDto register(RegisterDto dto) {

        if (clientRepository.existsByCpf(dto.cpf())) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.CONFLICT, "CPF já cadastrado");
        }
        if (clientRepository.existsByUsernameIgnoreCase(dto.username())) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.CONFLICT, "Username já em uso");
        }



        Client c = new Client();
        c.setName(dto.name());
        c.setCpf(dto.cpf());
        c.setUsername(dto.username());
        c.setEmail(dto.email());
        c.setPhone(dto.phone());
        c.setPassword(passwordEncoder.encode(dto.password()));

        Client saved = clientRepository.save(c);
        return clientMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public ClientDto login(LoginDto dto) {
        var opt = clientRepository.findByUsername(dto.username());
        if (opt.isEmpty()) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
        Client c = opt.get();
        if (!passwordEncoder.matches(dto.password(), c.getPassword())) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
        }
        
        // Forçar o carregamento dos endereços 
        c.getAddresses().size();
  
        return clientMapper.toDto(c);
    }

    @Transactional
    public void changePassword(PasswordChangeDTO dto) {
        Client c = clientRepository.findByCpf(dto.cpf())
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "CPF não encontrado"));
        if (!passwordEncoder.matches(dto.currentPassword(), c.getPassword())) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Senha atual incorreta");
        }
        c.setPassword(passwordEncoder.encode(dto.newPassword()));
        clientRepository.save(c);
    }

    @Transactional(readOnly = true)
    public ClientDto getClientById(Long id) {
        Client c = clientRepository.findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        c.getAddresses().size(); // Force eager loading
        return clientMapper.toDto(c);
    }
}
