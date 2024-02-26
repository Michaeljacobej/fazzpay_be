package io.github.fazzpay.fazzpay.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDate {
  private Long amount;
  private LocalDate date;
}
