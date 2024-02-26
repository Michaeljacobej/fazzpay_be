package io.github.fazzpay.fazzpay.exceptions.custom;

public class TransferSelfException extends RuntimeException {

  public TransferSelfException() {
    super("Cannot transfer to yourself");
  }

}
