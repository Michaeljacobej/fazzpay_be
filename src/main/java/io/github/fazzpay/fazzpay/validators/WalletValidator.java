package io.github.fazzpay.fazzpay.validators;

import java.util.Objects;

import io.github.fazzpay.fazzpay.exceptions.custom.IncorectPinException;
import io.github.fazzpay.fazzpay.exceptions.custom.NullPinException;
import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotEnoughException;
import io.github.fazzpay.fazzpay.models.Wallet;

public class WalletValidator {

  public static Boolean isEnoughBalance(Wallet wallet, Long amount) {
    if (wallet.getBalance() <= amount) {
      throw new ResourceNotEnoughException("Wallet", "balance", amount);
    }
    return true;
  }

  public static Boolean isValidPin(Wallet wallet, String input) {
    if (Objects.isNull(wallet.getPin())) {
      throw new NullPinException();
    }
    if (!wallet.getPin().equals(input)) {
      throw new IncorectPinException();
    }
    return true;
  }

}
