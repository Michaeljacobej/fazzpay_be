package io.github.fazzpay.fazzpay.exceptions.custom;

public class IncorectPinException extends RuntimeException {

  public IncorectPinException() {
    super("Wrong PIN");
  }

}
