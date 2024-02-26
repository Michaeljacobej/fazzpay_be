package io.github.fazzpay.fazzpay.services.deposit;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.DepositRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface DepositService {
    ResponseData createDeposit(DepositRequest request, User user);

    ResponseData readDeposit(Boolean status);
}
