package com.jogosparavida.games4life_backend.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jogosparavida.games4life_backend.dto.AccessoryDto;
import com.jogosparavida.games4life_backend.mapper.AccessoryMapper;
import com.jogosparavida.games4life_backend.model.Accessory;
import com.jogosparavida.games4life_backend.repository.AccessoryRepository;
import com.jogosparavida.games4life_backend.repository.RentalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessoryService {

	
	 private final AccessoryRepository accessoryRepository;
     private final RentalRepository rentalRepository;
	    private final AccessoryMapper accessoryMapper = new AccessoryMapper();

	    @Transactional(readOnly = true)
	    public List<AccessoryDto> getAllAccessories() {
	        return accessoryRepository.findAll()
	                                  .stream()
	                                  .map(accessoryMapper::toDto)
	                                  .collect(Collectors.toList());
	    }

		@Transactional(readOnly = true)
		@SuppressWarnings("null")
		public AccessoryDto getAccessory(Long id) {
	        Accessory accessory = accessoryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Acessório não encontrado"));
	        return accessoryMapper.toDto(accessory);
	    }

		@Transactional
		@SuppressWarnings("null")
		public AccessoryDto createAccessory(AccessoryDto dto) {
	        Accessory accessory = accessoryMapper.toEntity(dto);
	        Accessory saved = accessoryRepository.save(accessory);
	        return accessoryMapper.toDto(saved);
	    }

		@Transactional
		@SuppressWarnings("null")
		public AccessoryDto updateAccessory(Long id, AccessoryDto dto) {
	        Accessory accessory = accessoryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Acessório não encontrado"));

	        accessory.setName(dto.name());
	        accessory.setPrice(dto.price());
	        accessory.setImagem(dto.image());

	        Accessory updated = accessoryRepository.save(accessory);
	        return accessoryMapper.toDto(updated);
	    }

		@Transactional
		@SuppressWarnings("null")
		public void deleteAccessory(Long id) {
			Accessory accessory = accessoryRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Acessório não encontrado"));

			// Remover relacionamento com aluguéis antes de excluir para evitar violações de FK
			rentalRepository.findByAcessories_Id(id).forEach(r -> {
				r.getAcessories().removeIf(a -> a.getId().equals(id));
				rentalRepository.save(r);
			});

			accessoryRepository.delete(accessory);
		}
}
