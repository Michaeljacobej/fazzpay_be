package io.github.fazzpay.fazzpay.payloads.request;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {
  private String to;
  private String subject;
  private String message;
  private String template;
  private Map<String, Object> properties;
}
