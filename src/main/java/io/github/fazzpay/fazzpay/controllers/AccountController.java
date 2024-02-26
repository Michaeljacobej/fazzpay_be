package io.github.fazzpay.fazzpay.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.ChangeNameRequest;
import io.github.fazzpay.fazzpay.payloads.request.ChangePasswordRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.services.account.AccountService;
import io.github.fazzpay.fazzpay.services.user.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private UserService userService;

  @PutMapping("/name/change")
  public ResponseEntity<?> editName(@RequestBody @Valid ChangeNameRequest changeNameRequest,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    ResponseData responseData = accountService.changeName(changeNameRequest, user);
    return ResponseEntity.ok().body(responseData);
  }

  @PutMapping("/password/change")
  public ResponseEntity<?> editPassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    ResponseData responseData = userService.changePassword(changePasswordRequest, user);
    return ResponseEntity.ok().body(responseData);
  }

  @PostMapping("/image/upload")
  public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile multipartFile,
      Authentication authentication) throws IOException {
    ResponseData responseData = accountService.uploadImage(multipartFile, (User) authentication.getPrincipal());
    return ResponseEntity.ok().body(responseData);
  }

  @GetMapping("/me")
  public ResponseEntity<?> getAccount(Authentication authentication) {
    ResponseData responseData = userService.getAccount((User) authentication.getPrincipal());
    return ResponseEntity.ok().body(responseData);
  }

}
