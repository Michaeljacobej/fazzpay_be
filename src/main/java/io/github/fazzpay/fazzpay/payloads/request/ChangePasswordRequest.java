package io.github.fazzpay.fazzpay.payloads.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
  @NotBlank(message = "Password harus diisi!")
  private String password;

  @NotBlank(message = "Password baru harus diisi!")
  private String newPassword;
}
