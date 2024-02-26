package io.github.fazzpay.fazzpay.services.transfer;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.TransferRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface TransferService {
    // Create - add new transfer transaction
    ResponseData createTransferService(TransferRequest request, User user);
}
