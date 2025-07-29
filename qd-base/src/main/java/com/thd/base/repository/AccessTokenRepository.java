package com.thd.base.repository;

import com.thd.base.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccessTokenRepository extends JpaRepository<AccessToken, UUID> {
    Optional<AccessToken> findByToken(String token);
    @Query(value = "SELECT entity from AccessToken entity " +
            "where entity.username =:username " +
            "and entity.expired = false " +
            "and entity.revoked = false ")
    List<AccessToken> findAllByUsername(String username);
}
