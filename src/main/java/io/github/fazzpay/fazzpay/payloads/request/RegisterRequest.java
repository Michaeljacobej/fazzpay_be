package io.github.fazzpay.fazzpay.payloads.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "Nama harus diisi!")
  private String name;

  @NotBlank(message = "Email harus diisi!")
  private String email;

  @NotBlank(message = "Password harus diisi!")
  private String password;

  @NotBlank(message = "Pin harus diisi!")
  @Size(min = 6, max = 6, message = "Pin harus 6 digit")
  private String pin;

}
