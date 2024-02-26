package io.github.fazzpay.fazzpay.payloads.response;

import java.util.List;

import io.github.fazzpay.fazzpay.models.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private Long id;
  private String email;
  private String name;
  private String image;
  private List<Phone> phone;
  private String token;
}
