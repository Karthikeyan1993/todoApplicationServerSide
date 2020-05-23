package com.app.todo.repository;

import com.app.todo.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("tokenRepository")
public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByToken(String token);
}
