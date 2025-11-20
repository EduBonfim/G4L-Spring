package com.jogosparavida.games4life_backend.util;

import java.time.Instant;

import org.springframework.data.jpa.domain.Specification;

import com.jogosparavida.games4life_backend.model.Rental;

public class RentalSpecs {

	// filtros dinâmicos para consultas no banco pelo admin
	
	public static Specification<Rental> hasClientCpf(String cpf) {
	    return (root, q, cb) -> (cpf == null || cpf.isBlank()) ? cb.conjunction() : cb.equal(root.get("client").get("cpf"), cpf);
	  }
	
	public static Specification<Rental> hasConsoleId(Long id) {
	    return (root, q, cb) -> (id == null) ? cb.conjunction() : cb.equal(root.get("console").get("id"), id);
	  }
	  public static Specification<Rental> dateFrom(Instant from) {
	    return (root, q, cb) -> (from == null) ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("orderDate"), from);
	  }
	  public static Specification<Rental> dateTo(Instant to) {
	    return (root, q, cb) -> (to == null) ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("orderDate"), to);
	  }
	
}
