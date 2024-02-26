package io.github.fazzpay.fazzpay.services.payment;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.PaymentRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface PaymentService {
  public ResponseData createPayment(PaymentRequest request, User user);
}