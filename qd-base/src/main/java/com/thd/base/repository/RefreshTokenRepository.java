package com.thd.base.repository;

import com.thd.base.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    @Query(value = "SELECT entity from RefreshToken entity " +
            "where entity.username =:username " +
            "and entity.revoked = false")
    List<RefreshToken> findAllByUsername(String username);
}
