package io.github.fazzpay.fazzpay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.ContactRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.services.contact.ContactService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    private ResponseData responseData;

    @PostMapping
    public ResponseEntity<?> addUserContact(@RequestBody @Valid ContactRequest request,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        responseData = contactService.addUserContact(request, user);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping
    public ResponseEntity<?> getUserContact(Authentication authentication) {
        responseData = contactService.readUserContact((User) authentication.getPrincipal());
        return ResponseEntity.ok().body(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserContact(@PathVariable Long id) {
        responseData = contactService.deleteUserContact(id);
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<?> searchUser(@PathVariable String search, Authentication authentication) {
        responseData = contactService.search(search, (User) authentication.getPrincipal());
        return ResponseEntity.ok().body(responseData);
    }
}
