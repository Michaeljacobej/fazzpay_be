package io.github.fazzpay.fazzpay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.PinRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.services.wallet.WalletService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/wallet")
public class WalletController {

  @Autowired
  private WalletService walletService;

  @GetMapping("/balance")
  public ResponseEntity<?> getBalance(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    ResponseData responseData = walletService.getBalance(user);
    return ResponseEntity.ok().body(responseData);
  }

  @PutMapping("/changepin")
  public ResponseEntity<?> editPin(@RequestBody @Valid PinRequest pinRequest, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    ResponseData responseData = walletService.editPin(pinRequest, user);
    return ResponseEntity.ok().body(responseData);
  }

  @PostMapping("/verifpin")
  public ResponseEntity<?> verifPin(@RequestBody @Valid PinRequest pinRequest, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    ResponseData responseData = walletService.verifPin(pinRequest, user);
    return ResponseEntity.ok().body(responseData);
  }

}
