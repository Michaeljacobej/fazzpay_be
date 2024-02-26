package io.github.fazzpay.fazzpay.services.withdrawal;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.WithdrawalRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface WithdrawalService {
    public ResponseData createWithdrawalService(WithdrawalRequest withdrawalRequest, User user);
}
