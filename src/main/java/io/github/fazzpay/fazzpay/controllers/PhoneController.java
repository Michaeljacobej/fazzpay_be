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
import io.github.fazzpay.fazzpay.payloads.request.PhoneRequest;
import io.github.fazzpay.fazzpay.services.phone.PhoneService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/phone")
public class PhoneController {
    @Autowired
    private PhoneService userPhoneService;

    @PostMapping
    public ResponseEntity<?> addUserPhone(@RequestBody @Valid PhoneRequest request, Authentication authentication) {
        return ResponseEntity.ok()
                .body(userPhoneService.createUserPhone(request, (User) authentication.getPrincipal()));
    }

    @PostMapping("/set-primary")
    public ResponseEntity<?> setPrimary(@RequestBody @Valid PhoneRequest request, Authentication authentication) {
        return ResponseEntity.ok()
                .body(userPhoneService.makePhonePrimary(request, (User) authentication.getPrincipal()));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUserPhone(@RequestBody @Valid PhoneRequest request,
            Authentication authentication) {
        return ResponseEntity.ok()
                .body(userPhoneService.deleteUserPhone(request, (User) authentication.getPrincipal()));
    }

    @GetMapping
    public ResponseEntity<?> getUserPhone(Authentication authentication) {
        return ResponseEntity.ok(userPhoneService.getUserPhone((User) authentication.getPrincipal()));
    }
}
