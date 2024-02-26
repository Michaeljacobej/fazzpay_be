package io.github.fazzpay.fazzpay.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.fazzpay.fazzpay.models.Transaction;
import io.github.fazzpay.fazzpay.models.TransactionDate;
import io.github.fazzpay.fazzpay.models.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  public List<Transaction> findByFromUserId(Long id);

  public List<Transaction> findByToUserId(Long id);

  @Query("SELECT sum(t.amount) FROM Transaction t WHERE t.toUser = ?1 AND t.updatedAt BETWEEN ?2 AND CURRENT_DATE + 1")
  public Optional<Long> sumUserIncome(User user, LocalDateTime date);

  @Query("SELECT sum(t.amount) FROM Transaction t WHERE t.fromUser = ?1 AND t.updatedAt BETWEEN ?2 AND CURRENT_DATE + 1")
  public Optional<Long> sumUserExpense(User user, LocalDateTime date);

  @Query("""
      SELECT new io.github.fazzpay.fazzpay.models.TransactionDate(sum(t.amount) AS amount, CAST(t.updatedAt AS LocalDate) AS date)
      FROM Transaction t
      WHERE t.toUser = ?1 AND t.updatedAt BETWEEN ?2 AND CURRENT_DATE + 1
      GROUP BY CAST(t.updatedAt AS LocalDate)""")
  public List<TransactionDate> findUserIncomeGroupByDate(User user, LocalDateTime date);

  @Query("""
      SELECT new io.github.fazzpay.fazzpay.models.TransactionDate(sum(t.amount) AS amount, CAST(t.updatedAt AS LocalDate) AS date)
      FROM Transaction t
      WHERE t.fromUser = ?1 AND t.updatedAt BETWEEN ?2 AND CURRENT_DATE + 1
      GROUP BY CAST(t.updatedAt AS LocalDate)""")
  public List<TransactionDate> findUserExpenseGroupByDate(User user, LocalDateTime date);

}
