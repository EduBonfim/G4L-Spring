package com.jogosparavida.games4life_backend.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Console {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private BigDecimal price;

	private String imagem;
}
