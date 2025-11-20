package com.jogosparavida.games4life_backend.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date; // ⬅️ IMPORT NECESSÁRIO
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.jogosparavida.games4life_backend.dto.RentalCreateDto;
import com.jogosparavida.games4life_backend.dto.RentalDto;
import com.jogosparavida.games4life_backend.dto.RentalExtendDto;
import com.jogosparavida.games4life_backend.mapper.RentalMapper;
import com.jogosparavida.games4life_backend.model.Accessory;
import com.jogosparavida.games4life_backend.model.Client;
import com.jogosparavida.games4life_backend.model.Console;
import com.jogosparavida.games4life_backend.model.Game;
import com.jogosparavida.games4life_backend.model.Rental;
import com.jogosparavida.games4life_backend.repository.AccessoryRepository;
import com.jogosparavida.games4life_backend.repository.ClientRepository;
import com.jogosparavida.games4life_backend.repository.ConsoleRepository;
import com.jogosparavida.games4life_backend.repository.GameRepository;
import com.jogosparavida.games4life_backend.repository.RentalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final ClientRepository clientRepository;
    private final ConsoleRepository consoleRepository;
    private final GameRepository gameRepository;
    private final AccessoryRepository accessoryRepository;
    private final RentalMapper rentalMapper;
    
  

    @Transactional(readOnly = true)
    public List<RentalDto> getAllRentals() {
     System.out.println("[DEBUG] RentalService.getAllRentals() chamado");
        List<Rental> rentals = rentalRepository.findAll();
        System.out.println("[DEBUG] Rentals do banco: " + rentals.size());
        List<RentalDto> dtos = rentals.stream()
                .map(r -> {
                    try {
                        // Força o carregamento de games e accessories dentro da transação
                        r.getGames().size();
                        r.getAcessories().size();
                        RentalDto dto = rentalMapper.toDto(r);
                        System.out.println("[DEBUG] Rental " + r.getId() + " mapeado com sucesso");
                        return dto;
                    } catch (Exception e) {
                        System.out.println("[ERROR] Falha ao mapear rental " + r.getId() + ": " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                })
                .toList();
        System.out.println("[DEBUG] DTOs criados: " + dtos.size());
        return dtos;
    }

    @Transactional(readOnly = true)
    public List<RentalDto> getRentalsByClientCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) return java.util.List.of();
        // Estratégia segura: busca tudo e filtra em memória com isso evita erros de derivação
        return rentalRepository.findAll()
                .stream()
                .filter(r -> r.getClient() != null && cpf.equals(r.getClient().getCpf()))
                .map(rentalMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RentalDto getRental(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado"));
        return rentalMapper.toDto(rental);
    }

    @Transactional
    public RentalDto createRental(RentalCreateDto dto) {

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Console console = consoleRepository.findById(dto.consoleId())
                .orElseThrow(() -> new RuntimeException("Console não encontrado"));

        List<Game> games = gameRepository.findAllById(dto.gamesIds());
        List<Accessory> accessories = accessoryRepository.findAllById(dto.accessoriesIds());

        Rental rental = new Rental();
        rental.setClient(client);
        rental.setConsole(console);
        rental.setPlan(dto.plan());
        rental.setGames(games);
        rental.setAcessories(accessories);
        rental.setPurchaseOption(dto.purchaseOption());
        
        Instant orderDate = Instant.now();
        rental.setOrderDate(orderDate);

        long daysToAdd = 0;

        switch (dto.plan().toLowerCase()) {
            case "diário":
                daysToAdd = 1;
                break;
            case "semanal":
                daysToAdd = 7;
                break;
            case "anual":
                daysToAdd = 365;
                break;
            case "mensal":
            default:
                daysToAdd = 30;
                break;
            case "semestral":
                daysToAdd = 180;
                break;
        }
        // Calcula o preço inicial: console + jogos + acessórios + opcional de compra (sem incluir preço do plano)
        BigDecimal total = console.getPrice() != null ? console.getPrice() : BigDecimal.ZERO;
        for (Game g : games) {
            if (g.getPrice() != null) total = total.add(g.getPrice());
        }
        for (Accessory a : accessories) {
            if (a.getPrice() != null) total = total.add(a.getPrice());
        }
        if (Boolean.TRUE.equals(dto.purchaseOption())) {
            total = total.add(new BigDecimal("1800.00"));
        }

        rental.setTotalPrice(total); 

        // Calcula mantendo a mesma hora/minuto do orderDate
        Instant endInstant = orderDate.plusSeconds(daysToAdd * 86400);
        Date endDate = Date.from(endInstant);
        rental.setEndDate(endDate);

        Rental saved = rentalRepository.save(rental);
        return rentalMapper.toDto(saved);
    }


   
    @Transactional
    public RentalDto extendRental(Long rentalId, RentalExtendDto dto) {
        
        Rental rental = rentalRepository.findById(rentalId)
            .orElseThrow(() -> new RuntimeException("Aluguel não encontrado"));

        long daysToAdd = 0;
        BigDecimal priceToAdd = BigDecimal.ZERO;

        switch (dto.plan().toLowerCase()) {
            case "diário":
                daysToAdd = 1;
                priceToAdd = new BigDecimal("10.00");
                break;
            case "semanal":
                daysToAdd = 7;
                priceToAdd = new BigDecimal("50.00");
                break;
            case "anual":
                daysToAdd = 365;
                priceToAdd = new BigDecimal("500.00");
                break;
            case "mensal":
            default:
                daysToAdd = 30;
                priceToAdd = new BigDecimal("150.00");
                break;
            case "semestral":
                daysToAdd = 180;
                priceToAdd = new BigDecimal("800.00");
                break;
        }

        daysToAdd *= dto.mult();
        priceToAdd = priceToAdd.multiply(BigDecimal.valueOf(dto.mult()));

        // Extensão mantendo mesma hora
        Date currentUtilDate = rental.getEndDate();
        Instant currentEndInstant = Instant.ofEpochMilli(currentUtilDate.getTime());
        Instant newEndInstant = currentEndInstant.plusSeconds(daysToAdd * 86400);

        // Validação máximo 3 anos a partir da data de pedido
        Instant maxEndInstant = rental.getOrderDate().plusSeconds(3L * 365 * 86400);
        if (newEndInstant.isAfter(maxEndInstant)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Limite máximo de 3 anos atingido.");
        }

        rental.setEndDate(Date.from(newEndInstant));
        // NÃO somar ao total agora; só após confirmação de pagamento
        

        // Acumula o valor da extensão como pendente (será incorporado ao total após confirmação)
        BigDecimal currentPending = rental.getPendingExtensionAmount() != null ? rental.getPendingExtensionAmount() : BigDecimal.ZERO;
        rental.setPendingExtensionAmount(currentPending.add(priceToAdd));
        rental.setPendingExtensionStatus("pendente");
        
        Rental updatedRental = rentalRepository.save(rental);

        return rentalMapper.toDto(updatedRental);
    }
    
    @Transactional
    public RentalDto confirmPayment(Long rentalId) {
        
        // 1. Encontra o aluguel
        Rental rental = rentalRepository.findById(rentalId)
            .orElseThrow(() -> new RuntimeException("Aluguel não encontrado"));

        // 2. atualiza o campo
        rental.setPaymentConfirmed(true);

        // 3. Transfere valor pendente para o total e zera pendências
        BigDecimal pending = rental.getPendingExtensionAmount() != null ? rental.getPendingExtensionAmount() : BigDecimal.ZERO;
        if (pending.compareTo(BigDecimal.ZERO) > 0) {
            rental.setTotalPrice(rental.getTotalPrice().add(pending));
        }
        rental.setPendingExtensionAmount(BigDecimal.ZERO);
        rental.setPendingExtensionStatus("pago");

        // 4. Salva no banco
        Rental updatedRental = rentalRepository.save(rental);

        // 5. Retorna o DTO atualizado
        return rentalMapper.toDto(updatedRental);
    }
}