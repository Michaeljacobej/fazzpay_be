package io.github.fazzpay.fazzpay.services.transaction;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.TransferRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface TransactionService {

  public ResponseData transfer(TransferRequest transferRequest, User user);

  public ResponseData getHistory(User user);

  public ResponseData getTotal(User user);

  public ResponseData getWeekStats(User user);

}
