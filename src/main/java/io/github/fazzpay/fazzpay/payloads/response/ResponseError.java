package io.github.fazzpay.fazzpay.payloads.response;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
  private HttpStatusCode status;
  private String message;
  private Object errors;

  public Integer getStatus() {
    return status.value();
  }
}
