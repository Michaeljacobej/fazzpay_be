package io.github.fazzpay.fazzpay.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.fazzpay.fazzpay.models.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Optional<Phone> findByIsPrimary(Boolean isPrimary);

    Optional<Phone> findByUserIdAndIsPrimaryTrue(Long userId);

    List<Phone> findAllByUserId(Long userId);

    Optional<Phone> findFirstByUserId(Long userId);

    Optional<Phone> findByUserIdAndPhone(Long userId, String phone);

    Optional<Phone> findByPhone(String phone);

    Optional<Phone> findByPhoneAndUserIdIsNot(String phone, Long userId);
}
