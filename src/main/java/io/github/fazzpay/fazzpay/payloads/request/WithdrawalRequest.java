package io.github.fazzpay.fazzpay.payloads.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalRequest {

    @NotBlank(message = "Method harus diisi!")
    private String methodName;

    @NotBlank(message = "Pin harus diisi!")
    private String pin;

    private Long amount;

}
