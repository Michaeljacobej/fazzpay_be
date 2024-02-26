package io.github.fazzpay.fazzpay.services.contact;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.ContactRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface ContactService {
    ResponseData addUserContact(ContactRequest request, User user);

    ResponseData readUserContact(User user);

    ResponseData deleteUserContact(Long id);

    ResponseData search(String search, User user);
}
