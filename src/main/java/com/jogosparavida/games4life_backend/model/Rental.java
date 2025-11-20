package com.jogosparavida.games4life_backend.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date; // ⬅️ IMPORT ADICIONADO
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Client client;

	@ManyToOne(optional = false)
	private Console console;

	// algueis
	private String plan;

	@ManyToMany
	private List<Game> games = new ArrayList<>();

	@ManyToMany
	private List<Accessory> acessories = new ArrayList<>();

	// ver se o cliente pode comprar novamente
	private Boolean purchaseOption;

	@Column(precision = 12, scale = 2)
	private BigDecimal totalPrice;

	// guarda um tempo preciso no padrão
	@Column(nullable = false)
	private Instant orderDate;

   
    @Temporal(TemporalType.DATE) // Guarda a data (ex: 2025-11-17)
	private Date endDate;
    
    @Column(columnDefinition = "BOOLEAN DEFAULT false") // Começa como 'falso'
	private boolean paymentConfirmed = false;

    @Column(precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0")
	private BigDecimal pendingExtensionAmount = BigDecimal.ZERO;

    @Column(length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'pago'")
	private String pendingExtensionStatus = "pago";

}