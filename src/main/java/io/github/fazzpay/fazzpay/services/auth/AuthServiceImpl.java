package io.github.fazzpay.fazzpay.services.auth;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceAlreadyExistException;
import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotFoundException;
import io.github.fazzpay.fazzpay.models.Account;
import io.github.fazzpay.fazzpay.models.PasswordResetToken;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Phone;
import io.github.fazzpay.fazzpay.models.Wallet;
import io.github.fazzpay.fazzpay.payloads.request.AuthRequest;
import io.github.fazzpay.fazzpay.payloads.request.ConfirmResetPasswordRequest;
import io.github.fazzpay.fazzpay.payloads.request.MailRequest;
import io.github.fazzpay.fazzpay.payloads.request.RegisterRequest;
import io.github.fazzpay.fazzpay.payloads.request.EmailRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.payloads.response.UserResponse;
import io.github.fazzpay.fazzpay.repositories.AccountRepository;
import io.github.fazzpay.fazzpay.repositories.PasswordResetTokenRepository;
import io.github.fazzpay.fazzpay.repositories.PhoneRepository;
import io.github.fazzpay.fazzpay.repositories.UserRepository;
import io.github.fazzpay.fazzpay.repositories.WalletRepository;
import io.github.fazzpay.fazzpay.services.jwt.JwtService;
import io.github.fazzpay.fazzpay.services.mail.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private WalletRepository walletRepository;

  @Autowired
  private PasswordResetTokenRepository passwordResetTokenRepository;

  @Autowired
  private PhoneRepository userPhoneRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private EmailService emailService;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public ResponseData login(AuthRequest request) {
    authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));
    String jwtToken = jwtService.generateToken(user);
    List<Phone> userPhone = userPhoneRepository.findAllByUserId(user.getId());
    UserResponse responseUser = new UserResponse();
    responseUser.setId(user.getId());
    responseUser.setEmail(user.getEmail());
    responseUser.setName(user.getAccount().getName());
    responseUser.setImage(user.getAccount().getImage());
    responseUser.setPhone(userPhone);
    responseUser.setToken(jwtToken);
    return new ResponseData(HttpStatus.OK, "Auth Success", responseUser);
  }

  @Override
  public ResponseData register(RegisterRequest request) throws MessagingException {
    userRepository.findByEmail(request.getEmail()).ifPresent(t -> {
      throw new ResourceAlreadyExistException("User", "email", request.getEmail());
    });
    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    Account account = new Account();
    account.setUser(user);
    account.setName(request.getName());
    Wallet wallet = new Wallet();
    wallet.setUser(user);
    wallet.setPin(request.getPin());
    user.setAccount(account);
    user.setWallet(wallet);
    userRepository.save(user);
    accountRepository.save(account);
    walletRepository.save(wallet);

    Map<String, Object> properties = new HashMap<>();
    properties.put("name", user.getAccount().getName());

    MailRequest emailRequest = new MailRequest();
    emailRequest.setTo(user.getEmail());
    emailRequest.setSubject("Selamat Datang di FazzPay");
    emailRequest.setTemplate("welcome-mail.html");
    emailRequest.setProperties(properties);

    emailService.sendEmail(emailRequest);
    // String jwtToken = jwtService.generateToken(user);
    return new ResponseData(HttpStatus.CREATED, "Register Success", null);
  }

  @Override
  public ResponseData requestResetPassword(EmailRequest request) throws MessagingException {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));
    PasswordResetToken passwordResetToken = new PasswordResetToken();
    passwordResetToken.generateResetToken();
    passwordResetToken.setUser(user);
    passwordResetToken.setExpiryDate(LocalDateTime.now().plusDays(1));
    while (passwordResetTokenRepository.existsByToken(passwordResetToken.getToken())) {
      passwordResetToken.generateResetToken();
    }
    passwordResetTokenRepository.save(passwordResetToken);
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", user.getAccount().getName());
    properties.put("code", passwordResetToken.getToken());

    MailRequest emailRequest = new MailRequest();
    emailRequest.setTo(user.getEmail());
    emailRequest.setSubject("Permintaan Reset Password");
    emailRequest.setTemplate("reset-password.html");
    emailRequest.setProperties(properties);

    emailService.sendEmail(emailRequest);
    return new ResponseData(HttpStatus.OK, "Reset Request Success", null);
  }

  @Override
  public ResponseData resetPassword(ConfirmResetPasswordRequest request) {
    PasswordResetToken passwordResetToken = passwordResetTokenRepository
        .findByTokenAndUserEmail(request.getToken(), request.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("Reset Token", "token", request.getToken()));
    if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      passwordResetTokenRepository.delete(passwordResetToken);
      throw new RuntimeException("Your token has expired");
    }
    User user = passwordResetToken.getUser();
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
    passwordResetTokenRepository.delete(passwordResetToken);
    return new ResponseData(HttpStatus.OK, "Reset Password Success", null);
  }

  @Override
  public ResponseData checkEmail(EmailRequest request) {
    userRepository.findByEmail(request.getEmail()).ifPresent(t -> {
      throw new ResourceAlreadyExistException("User", "email", request.getEmail());
    });
    return new ResponseData(HttpStatus.OK, "Success", null);
  }

}
