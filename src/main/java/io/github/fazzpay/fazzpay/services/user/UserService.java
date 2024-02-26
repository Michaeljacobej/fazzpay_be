package io.github.fazzpay.fazzpay.services.user;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.ChangePasswordRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface UserService {

  public ResponseData changePassword(ChangePasswordRequest userRequest, User user);

  public ResponseData getAccount(User user);

}
