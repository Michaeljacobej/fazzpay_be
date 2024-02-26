package io.github.fazzpay.fazzpay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.WithdrawalRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.services.Method.MethodService;
import io.github.fazzpay.fazzpay.services.withdrawal.WithdrawalService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/withdrawal")
public class WithdrawalController {
    @Autowired
    WithdrawalService withdrawalService;

    @Autowired
    MethodService methodService;

    @PostMapping
    public ResponseEntity<?> addWithdrawal(@RequestBody @Valid WithdrawalRequest withdrawalRequest,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ResponseData responseData = withdrawalService.createWithdrawalService(withdrawalRequest, user);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/method")
    public ResponseEntity<?> getAllMethod() {
        ResponseData responseData = methodService.readAllMethod();
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

}
