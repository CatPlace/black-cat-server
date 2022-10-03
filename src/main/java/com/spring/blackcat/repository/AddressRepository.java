package com.spring.blackcat.repository;

import com.spring.blackcat.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    public Address findBySido(String sido);
}
