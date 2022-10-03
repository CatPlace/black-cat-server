package com.spring.blackcat.address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    public Address findBySido(String sido);
}
