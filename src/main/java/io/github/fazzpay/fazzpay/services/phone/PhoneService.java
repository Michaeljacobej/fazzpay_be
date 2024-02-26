package io.github.fazzpay.fazzpay.services.phone;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.PhoneRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface PhoneService {

    public ResponseData createUserPhone(PhoneRequest request, User user);

    public ResponseData makePhonePrimary(PhoneRequest request, User user);

    public ResponseData deleteUserPhone(PhoneRequest request, User user);

    public ResponseData getUserPhone(User user);

}
