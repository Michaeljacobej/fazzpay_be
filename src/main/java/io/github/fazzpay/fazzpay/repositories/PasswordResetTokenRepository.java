package io.github.fazzpay.fazzpay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.fazzpay.fazzpay.models.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

  public boolean existsByToken(String token);

  public Optional<PasswordResetToken> findByTokenAndUserEmail(String token, String email);

}
