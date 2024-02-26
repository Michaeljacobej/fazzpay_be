package io.github.fazzpay.fazzpay.payloads.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmResetPasswordRequest {

  @NotBlank(message = "Email harus diisi!")
  private String email;

  @NotBlank(message = "Token harus diisi!")
  private String token;

  @NotBlank(message = "Password harus diisi!")
  private String password;

}
