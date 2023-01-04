package com.spring.blackcat.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findBySido(String sido);

//    Page<Address> findBySearchStringContains(Pageable pageable, String findString);
}
