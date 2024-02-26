package io.github.fazzpay.fazzpay.services.mail;

import io.github.fazzpay.fazzpay.payloads.request.MailRequest;
import jakarta.mail.MessagingException;

public interface EmailService {
  public void sendEmail(MailRequest request) throws MessagingException;
}
