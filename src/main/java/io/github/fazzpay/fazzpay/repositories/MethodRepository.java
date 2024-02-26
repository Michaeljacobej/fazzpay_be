package io.github.fazzpay.fazzpay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.fazzpay.fazzpay.models.Method;

public interface MethodRepository extends JpaRepository<Method, Long> {

    Optional<Method> findByName(String methodName);

}
