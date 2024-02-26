package io.github.fazzpay.fazzpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.fazzpay.fazzpay.models.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
