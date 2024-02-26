package io.github.fazzpay.fazzpay.exceptions.custom;

public class NullPinException extends RuntimeException {

  public NullPinException() {
    super("PIN is not yet created");
  }

}
