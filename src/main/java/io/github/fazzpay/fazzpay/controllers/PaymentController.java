package io.github.fazzpay.fazzpay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.PaymentRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.services.payment.PaymentService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {
  @Autowired
  private PaymentService paymentService;

  @PostMapping("/post")
  public ResponseEntity<?> payment(@RequestBody @Valid PaymentRequest PaymentRequest, Authentication authentication) {
    ResponseData responseData = paymentService.createPayment(PaymentRequest, (User) authentication.getPrincipal());
    return ResponseEntity.ok(responseData);
  }

}
