package io.github.fazzpay.fazzpay.services.auth;

import io.github.fazzpay.fazzpay.payloads.request.AuthRequest;
import io.github.fazzpay.fazzpay.payloads.request.ConfirmResetPasswordRequest;
import io.github.fazzpay.fazzpay.payloads.request.RegisterRequest;
import io.github.fazzpay.fazzpay.payloads.request.EmailRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import jakarta.mail.MessagingException;

public interface AuthService {

  public ResponseData register(RegisterRequest request) throws MessagingException;

  public ResponseData login(AuthRequest request);

  public ResponseData requestResetPassword(EmailRequest request) throws MessagingException;

  public ResponseData resetPassword(ConfirmResetPasswordRequest request);

  public ResponseData checkEmail(EmailRequest request);

}
