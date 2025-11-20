package com.jogosparavida.games4life_backend.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity 
@Table(name = "clients")
@Data
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank 
	private String name;
	  @NotNull @Column(nullable=false , unique = true) 
	  @CPF
	  private String cpf; 
	  @Email @NotBlank
	  private String email;
	  @NotBlank
	  private String phone;
	  @NotBlank
	  @Column(unique = true)
	  private String username;
	  
	  @JsonIgnore
	  @NotBlank
	  private String password;
	  
	  
	  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	  private List<Address> addresses = new ArrayList<>();

	  public void addAddress(Address address) {
	      addresses.add(address);
	      address.setClient(this);
	  }

	  public void removeAddress(Address address) {
	      addresses.remove(address);
	      address.setClient(null);
	  }
	}

