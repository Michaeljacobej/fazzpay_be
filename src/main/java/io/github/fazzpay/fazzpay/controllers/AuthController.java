package io.github.fazzpay.fazzpay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.fazzpay.fazzpay.payloads.request.AuthRequest;
import io.github.fazzpay.fazzpay.payloads.request.ConfirmResetPasswordRequest;
import io.github.fazzpay.fazzpay.payloads.request.RegisterRequest;
import io.github.fazzpay.fazzpay.payloads.request.EmailRequest;
import io.github.fazzpay.fazzpay.services.auth.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) throws MessagingException {
    return ResponseEntity.ok().body(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
    return ResponseEntity.ok().body(authService.login(request));
  }

  @PostMapping("/request-reset")
  public ResponseEntity<?> requestResetPassword(@RequestBody @Valid EmailRequest request)
      throws MessagingException {
    return ResponseEntity.ok().body(authService.requestResetPassword(request));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody @Valid ConfirmResetPasswordRequest request) {
    return ResponseEntity.ok().body(authService.resetPassword(request));
  }

  @PostMapping("/check-email")
  public ResponseEntity<?> checkkEmail(@RequestBody @Valid EmailRequest request) {
    return ResponseEntity.ok().body(authService.checkEmail(request));
  }

}
