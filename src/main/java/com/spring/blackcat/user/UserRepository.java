package com.spring.blackcat.user;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.common.code.Gender;
import com.spring.blackcat.common.code.ProviderType;
import com.spring.blackcat.common.code.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findUserByProviderIdAndProviderType(String providerId, ProviderType providerType);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u set u.nickname=:nickname, u.dateOfBirth=:dateOfBirth, u.gender=:gender where u.id=:userId")
    int updateAdditionalInfo(@Param(value = "nickname") String nickname,
                             @Param(value = "dateOfBirth") Date dateOfBirth, @Param(value = "gender") Gender gender, @Param(value = "userId") Long userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u set u.address=:address, u.openChatLink=:openChatLink, u.role=:role where u.id=:userId")
    int updateTattooistInfo(@Param(value = "address") Address address,
                            @Param(value = "openChatLink") String openChatLink, @Param(value = "role") Role role, @Param(value = "userId") Long userId);
}
