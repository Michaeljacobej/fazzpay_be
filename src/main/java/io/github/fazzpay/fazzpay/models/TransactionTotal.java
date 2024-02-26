package io.github.fazzpay.fazzpay.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTotal {
  private Long income;
  private Long expense;
  private LocalDateTime date;
}
