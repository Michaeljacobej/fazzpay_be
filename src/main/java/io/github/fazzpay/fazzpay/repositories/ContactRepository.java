package io.github.fazzpay.fazzpay.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.fazzpay.fazzpay.models.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
  List<Contact> findByUserId(Long userId);

  Boolean existsByUserIdAndContactId(Long userId, Long contactId);
}
