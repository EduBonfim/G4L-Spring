package com.jogosparavida.games4life_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String address; 

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false)
    private String number;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String district;

    @NotBlank
    @Pattern(regexp = "\\d{5}-?\\d{3}")
    @Column(nullable = false, length = 9)
    private String cep;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String city;

    @NotBlank
    @Size(min = 2, max = 2)
    @Pattern(regexp = "[A-Z]{2}")
    @Column(nullable = false, length = 2)
    private String uf;

    @Size(max = 100)
    private String reference;

    @NotBlank
    @Pattern(regexp = "Casa|Trabalho")
    @Column(nullable = false, length = 20)
    private String type;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
}

