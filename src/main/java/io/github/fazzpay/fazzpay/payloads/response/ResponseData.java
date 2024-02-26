package io.github.fazzpay.fazzpay.payloads.response;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
  private HttpStatusCode status;
  private String message;
  private Object data;

  public Integer getStatus() {
    return status.value();
  }
}
