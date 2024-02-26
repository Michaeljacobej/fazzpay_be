package io.github.fazzpay.fazzpay.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {
  private Long id;
  private String name;
  private TransactionType type;
  private Long amount;
  private LocalDateTime date;
}
