package com.jogosparavida.games4life_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jogosparavida.games4life_backend.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	 List<Address> findByClientId(Long clientId);

	    Long countByClientId(Long clientId);

	    @Modifying
	    @Query("UPDATE Address a SET a.isDefault = false WHERE a.client.id = :clientId")
	    void clearDefaultAddresses(@Param("clientId") Long clientId);
}
