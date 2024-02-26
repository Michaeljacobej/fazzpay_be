package io.github.fazzpay.fazzpay.services.wallet;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.PinRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface WalletService {

  public ResponseData getBalance(User user);

  public ResponseData editPin(PinRequest pinRequest, User user);

  public ResponseData verifPin(PinRequest pinRequest, User user);

}
