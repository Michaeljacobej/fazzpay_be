package io.github.fazzpay.fazzpay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.fazzpay.fazzpay.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
  public Optional<Account> findByUserId(Long id);
}
