package com.trapuce.marketplace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trapuce.marketplace.models.EmailConfirmationToken;

@Repository
public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken ,Long>{
    Optional<EmailConfirmationToken> findByToken(String token);
}
