package io.github.fazzpay.fazzpay.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.fazzpay.fazzpay.models.TransactionDate;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Withdrawal;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

  public List<Withdrawal> findByUserId(Long id);

  @Query("SELECT sum(w.amount) FROM Withdrawal w WHERE w.user = ?1 AND w.createdAt BETWEEN ?2 AND CURRENT_DATE + 1")
  public Optional<Long> sumUserWithdrawal(User user, LocalDateTime date);

  @Query("SELECT new io.github.fazzpay.fazzpay.models.TransactionDate(sum(w.amount) AS amount, CAST(w.createdAt AS LocalDate) AS date) FROM Withdrawal w WHERE w.user = ?1 AND w.createdAt BETWEEN ?2 AND CURRENT_DATE + 1 GROUP BY CAST(w.createdAt AS LocalDate)")
  public List<TransactionDate> findUserWithdrawalGroupByDate(User user, LocalDateTime date);

}
