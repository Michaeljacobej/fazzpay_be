package io.github.fazzpay.fazzpay.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.TransferRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.services.transaction.TransactionService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @PostMapping("/transfer")
  public ResponseEntity<?> transfer(@RequestBody @Valid TransferRequest transferRequest,
      Authentication authentication) {
    ResponseData responseData = transactionService.transfer(transferRequest, (User) authentication.getPrincipal());
    return ResponseEntity.ok().body(responseData);
  }

  @GetMapping
  public ResponseEntity<?> getHistory(Authentication authentication) {
    ResponseData responseData = transactionService.getHistory((User) authentication.getPrincipal());
    return ResponseEntity.ok().body(responseData);
  }

  @GetMapping("/total")
  public ResponseEntity<?> getTotal(Authentication authentication) {
    ResponseData responseData = transactionService.getTotal((User) authentication.getPrincipal());
    return ResponseEntity.ok().body(responseData);
  }

  @GetMapping("/weekly")
  public ResponseEntity<?> getWeekStats(Authentication authentication) {
    ResponseData responseData = transactionService.getWeekStats((User) authentication.getPrincipal());
    return ResponseEntity.ok().body(responseData);
  }

}
