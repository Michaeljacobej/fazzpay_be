package io.github.fazzpay.fazzpay.payloads.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {
    @NotNull(message = "Contact harus diisi")
    private Long contactId;
}
