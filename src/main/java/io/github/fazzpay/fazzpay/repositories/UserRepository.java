package io.github.fazzpay.fazzpay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.fazzpay.fazzpay.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findById(Long id);

  Optional<User> findByEmailAndIdIsNot(String email, Long userId);

}
