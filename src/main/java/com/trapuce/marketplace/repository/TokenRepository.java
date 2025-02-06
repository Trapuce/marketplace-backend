package com.trapuce.marketplace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trapuce.marketplace.models.Token;
import com.trapuce.marketplace.models.User;

@Repository
public interface TokenRepository extends JpaRepository<Token ,Long>{
    Optional<Token> findByTokenValue(String token);

    void deleteByUser(User user);
}
