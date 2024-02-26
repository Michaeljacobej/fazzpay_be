package io.github.fazzpay.fazzpay.payloads.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

  @NotNull(message = "Tujuan harus diisi!")
  private Long to;

  @NotNull(message = "Total harus diisi!")
  @Positive(message = "Total harus positif!")
  private Long amount;

  private String notes;

}
