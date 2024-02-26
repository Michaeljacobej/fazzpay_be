package io.github.fazzpay.fazzpay.services.transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotFoundException;
import io.github.fazzpay.fazzpay.exceptions.custom.TransferSelfException;
import io.github.fazzpay.fazzpay.models.Deposit;
import io.github.fazzpay.fazzpay.models.Transaction;
import io.github.fazzpay.fazzpay.models.TransactionDate;
import io.github.fazzpay.fazzpay.models.TransactionDateTotal;
import io.github.fazzpay.fazzpay.models.TransactionHistory;
import io.github.fazzpay.fazzpay.models.TransactionTotal;
import io.github.fazzpay.fazzpay.models.TransactionType;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Wallet;
import io.github.fazzpay.fazzpay.models.Withdrawal;
import io.github.fazzpay.fazzpay.payloads.request.TransferRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.repositories.DepositRepository;
import io.github.fazzpay.fazzpay.repositories.TransactionRepository;
import io.github.fazzpay.fazzpay.repositories.UserRepository;
import io.github.fazzpay.fazzpay.repositories.WalletRepository;
import io.github.fazzpay.fazzpay.repositories.WithdrawalRepository;
import io.github.fazzpay.fazzpay.utils.DateHelper;
import io.github.fazzpay.fazzpay.validators.WalletValidator;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private WalletRepository walletRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private DepositRepository depositRepository;

  @Autowired
  private WithdrawalRepository withdrawalRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public ResponseData transfer(TransferRequest transferRequest, User user) {
    if (user.getId().equals(transferRequest.getTo())) {
      throw new TransferSelfException();
    }
    User userTo = userRepository.findById(transferRequest.getTo())
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", transferRequest.getTo()));
    Wallet wallet = walletRepository.findByUserId(user.getId()).orElseThrow();
    Wallet walletTo = walletRepository.findByUserId(userTo.getId()).orElseThrow();
    WalletValidator.isEnoughBalance(wallet, transferRequest.getAmount());
    Transaction transaction = new Transaction();
    transaction.setFromUser(user);
    transaction.setToUser(userTo);
    transaction.setAmount(transferRequest.getAmount());
    if (!StringUtils.isNullOrEmpty(transferRequest.getNotes())) {
      transaction.setNotes(transferRequest.getNotes());
    }
    wallet.setBalance(wallet.getBalance() - transferRequest.getAmount());
    walletTo.setBalance(walletTo.getBalance() + transferRequest.getAmount());
    walletRepository.saveAll(List.of(wallet, walletTo));
    transactionRepository.save(transaction);
    return new ResponseData(HttpStatus.OK, "Success", transaction);
  }

  @Override
  public ResponseData getHistory(User user) {
    List<Transaction> transFrom = transactionRepository.findByFromUserId(user.getId());
    List<Transaction> transTo = transactionRepository.findByToUserId(user.getId());
    List<Deposit> deposits = depositRepository.findByUserId(user.getId());
    List<Withdrawal> withdrawals = withdrawalRepository.findByUserId(user.getId());

    List<TransactionHistory> transHistoryTransferExpense = transFrom.stream().map(t -> {
      return new TransactionHistory(t.getId(), t.getToUser().getAccount().getName(),
          TransactionType.TRANSFER, t.getAmount() * -1, t.getUpdatedAt());
    }).toList();
    List<TransactionHistory> transHistoryTransferIncome = transTo.stream().map(t -> {
      return new TransactionHistory(t.getId(), t.getFromUser().getAccount().getName(),
          TransactionType.TRANSFER, t.getAmount(), t.getUpdatedAt());
    }).toList();
    List<TransactionHistory> transHistoryDeposit = deposits.stream().map(t -> {
      return new TransactionHistory(t.getId(), t.getUser().getAccount().getName(), TransactionType.DEPOSIT,
          t.getAmount(), t.getCreatedAt());
    }).toList();
    List<TransactionHistory> transHistoryWithdrawal = withdrawals.stream().map(t -> {
      return new TransactionHistory(t.getId(), t.getUser().getAccount().getName(), TransactionType.WITHDRAW,
          t.getAmount() * -1, t.getCreatedAt());
    }).toList();

    List<TransactionHistory> transaction = new ArrayList<>();
    transaction.addAll(transHistoryTransferExpense);
    transaction.addAll(transHistoryTransferIncome);
    transaction.addAll(transHistoryDeposit);
    transaction.addAll(transHistoryWithdrawal);
    transaction.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
    return new ResponseData(HttpStatus.OK, "Success", transaction);
  }

  @Override
  public ResponseData getTotal(User user) {
    LocalDate date = LocalDate.now().minusDays(7);
    Long sumIncome = transactionRepository.sumUserIncome(user, date.atStartOfDay()).orElse(0L);
    Long sumExpense = transactionRepository.sumUserExpense(user, date.atStartOfDay()).orElse(0L);
    Long sumDeposit = depositRepository.sumUserDeposit(user, date.atStartOfDay()).orElse(0L);
    Long sumWithdrawal = withdrawalRepository.sumUserWithdrawal(user, date.atStartOfDay()).orElse(0L);
    TransactionTotal transTotal = new TransactionTotal(sumIncome + sumDeposit, sumExpense + sumWithdrawal,
        LocalDateTime.now());
    return new ResponseData(HttpStatus.OK, "Success", transTotal);
  }

  @Override
  public ResponseData getWeekStats(User user) {
    LocalDate date = LocalDate.now().minusDays(7);
    List<TransactionDate> transWeekIncome = transactionRepository.findUserIncomeGroupByDate(user,
        date.atStartOfDay());
    List<TransactionDate> transWeekExpense = transactionRepository.findUserExpenseGroupByDate(user,
        date.atStartOfDay());
    List<TransactionDate> transWeekDeposit = depositRepository.findUserDepositGroupByDate(user,
        date.atStartOfDay());
    List<TransactionDate> transWeekWithdrawal = withdrawalRepository.findUserWithdrawalGroupByDate(user,
        date.atStartOfDay());

    Map<String, Map<String, Long>> allTransWeek = new HashMap<>();
    DateHelper.getDatesInLastSevenDays().stream().forEach(
        t -> {
          Map<String, Long> transDate = new HashMap<>();
          transDate.put("income", 0L);
          transDate.put("expense", 0L);
          allTransWeek.put(t, transDate);
        });

    transWeekIncome.stream().forEach(t -> {
      Map<String, Long> getTransAtDate = allTransWeek.get(t.getDate().toString());
      getTransAtDate.put("income", getTransAtDate.get("income") + t.getAmount());
    });
    transWeekExpense.stream().forEach(t -> {
      Map<String, Long> getTransAtDate = allTransWeek.get(t.getDate().toString());
      getTransAtDate.put("expense", getTransAtDate.get("expense") + t.getAmount());
    });
    transWeekDeposit.stream().forEach(t -> {
      Map<String, Long> getTransAtDate = allTransWeek.get(t.getDate().toString());
      getTransAtDate.put("income", getTransAtDate.get("income") + t.getAmount());
    });
    transWeekWithdrawal.stream().forEach(t -> {
      Map<String, Long> getTransAtDate = allTransWeek.get(t.getDate().toString());
      getTransAtDate.put("expense", getTransAtDate.get("expense") + t.getAmount());
    });

    List<TransactionDateTotal> transaction = new ArrayList<>();
    allTransWeek.entrySet().stream().forEach(t -> {
      Long income = Optional.ofNullable(t.getValue().get("income")).orElse(0L);
      Long expense = Optional.ofNullable(t.getValue().get("expense")).orElse(0L);
      TransactionDateTotal tdTotal = new TransactionDateTotal(income, expense, LocalDate.parse(t.getKey()));
      transaction.add(tdTotal);
    });
    transaction.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
    return new ResponseData(HttpStatus.OK, "Success", transaction);
  }

}
