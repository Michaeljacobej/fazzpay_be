package io.github.fazzpay.fazzpay.payloads.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

  @NotNull(message = "Merchant harus diisi!")
  private Long merchantId;

  @NotNull(message = "Jumlah harus diisi!")
  private Long amount;

}