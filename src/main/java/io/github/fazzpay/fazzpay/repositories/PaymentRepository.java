package io.github.fazzpay.fazzpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.fazzpay.fazzpay.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
