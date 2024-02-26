package io.github.fazzpay.fazzpay.services.mail;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import io.github.fazzpay.fazzpay.payloads.request.MailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private SpringTemplateEngine templateEngine;

  @Override
  public void sendEmail(MailRequest request) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        StandardCharsets.UTF_8.name());

    Context context = new Context();
    context.setVariables(request.getProperties());

    helper.setFrom("no-reply@fazzpay.com");
    helper.setTo(request.getTo());
    helper.setSubject(request.getSubject());
    String html = templateEngine.process(request.getTemplate(), context);
    helper.setText(html, true);

    javaMailSender.send(message);
  }

}
