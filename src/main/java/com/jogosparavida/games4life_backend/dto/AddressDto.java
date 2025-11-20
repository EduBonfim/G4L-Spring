package com.jogosparavida.games4life_backend.dto;




public record AddressDto(
    Long id,
    String address,
    String number,
    String district,
    String cep,
    String city,
    String uf,
    String reference,
    String type,
    Boolean isDefault
) {
    // Getter adicional para compatibilidade com o frontend que usa 'default'
    public Boolean getDefault() {
        return isDefault;
    }
}
