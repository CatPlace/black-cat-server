package com.spring.blackcat.user;

import com.spring.blackcat.common.code.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findUserByProviderIdAndProviderType(String providerId, ProviderType providerType);
}
