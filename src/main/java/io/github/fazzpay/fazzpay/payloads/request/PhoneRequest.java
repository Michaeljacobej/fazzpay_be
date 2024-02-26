package io.github.fazzpay.fazzpay.payloads.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequest {

    @NotBlank(message = "Phone number is required")
    private String phone;

}
