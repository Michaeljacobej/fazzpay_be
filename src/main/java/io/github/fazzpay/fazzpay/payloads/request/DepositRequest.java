package io.github.fazzpay.fazzpay.payloads.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {
    @NotNull(message = "Total harus diisi!")
    @Positive(message = "Total harus positif!")
    private Long amount;
}
