package io.github.fazzpay.fazzpay.services.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Wallet;
import io.github.fazzpay.fazzpay.payloads.request.PaymentRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.repositories.PaymentRepository;
import io.github.fazzpay.fazzpay.repositories.WalletRepository;
import io.github.fazzpay.fazzpay.validators.WalletValidator;
import jakarta.transaction.Transactional;
import io.github.fazzpay.fazzpay.repositories.MerchantRepository;
import io.github.fazzpay.fazzpay.models.Merchant;
import io.github.fazzpay.fazzpay.models.Payment;
import io.github.fazzpay.fazzpay.models.TransactionStatus;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private MerchantRepository merchantRepository;

  @Autowired
  private WalletRepository walletRepository;

  @Override
  public ResponseData createPayment(PaymentRequest request, User user) {
    Merchant merchant = merchantRepository.findById(request.getMerchantId()).orElseThrow();
    Wallet wallet = walletRepository.findByUserId(user.getId()).orElseThrow();
    WalletValidator.isEnoughBalance(wallet, request.getAmount());
    Payment payment = new Payment();
    payment.setMerchant(merchant);
    payment.setUser(user);
    payment.setStatus(TransactionStatus.CONFIRMED);
    payment.setAmount(request.getAmount());
    wallet.setBalance(wallet.getBalance() - request.getAmount());
    walletRepository.save(wallet);
    paymentRepository.save(payment);
    return new ResponseData(HttpStatus.OK, "Success", payment);
  }

}