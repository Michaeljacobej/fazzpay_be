package io.github.fazzpay.fazzpay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.DepositRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.services.deposit.DepositService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/deposit")
public class DepositController {
    @Autowired
    private DepositService depositService;
  
    private ResponseData responseData;

    @PostMapping
    public ResponseEntity<?> createDeposit(@RequestBody @Valid DepositRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        responseData = depositService.createDeposit(request, user);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping
    public ResponseEntity<?> getDeposit(@RequestParam(value = "status", defaultValue = "") Boolean status) {
        responseData = depositService.readDeposit(status);
        return ResponseEntity.ok().body(responseData);
    }
}
