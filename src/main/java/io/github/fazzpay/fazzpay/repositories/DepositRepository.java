package io.github.fazzpay.fazzpay.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.fazzpay.fazzpay.models.Deposit;
import io.github.fazzpay.fazzpay.models.TransactionDate;
import io.github.fazzpay.fazzpay.models.User;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

  public List<Deposit> findByUserId(Long id);

  @Query("SELECT sum(d.amount) FROM Deposit d WHERE d.user = ?1 AND d.createdAt BETWEEN ?2 AND CURRENT_DATE + 1")
  public Optional<Long> sumUserDeposit(User user, LocalDateTime date);

  @Query("SELECT new io.github.fazzpay.fazzpay.models.TransactionDate(sum(d.amount) AS amount, CAST(d.createdAt AS LocalDate) AS date) FROM Deposit d WHERE d.user = ?1 AND d.createdAt BETWEEN ?2 AND CURRENT_DATE + 1 GROUP BY CAST(d.createdAt AS LocalDate)")
  public List<TransactionDate> findUserDepositGroupByDate(User user, LocalDateTime date);

}
