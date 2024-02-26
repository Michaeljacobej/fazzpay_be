package io.github.fazzpay.fazzpay.services.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotFoundException;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Wallet;
import io.github.fazzpay.fazzpay.payloads.request.PinRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.repositories.UserRepository;
import io.github.fazzpay.fazzpay.repositories.WalletRepository;
import io.github.fazzpay.fazzpay.validators.WalletValidator;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {

  @Autowired
  private WalletRepository walletRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public ResponseData getBalance(User user) {
    Wallet wallet = walletRepository.findByUserId(user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Wallet", "id", user.getId()));
    return new ResponseData(HttpStatus.OK, "Success", wallet);
  }

  @Override
  public ResponseData editPin(PinRequest pinRequest, User user) {
    User userFound = userRepository.findById(user.getId()).orElseThrow();
    Wallet wallet = userFound.getWallet();
    wallet.setUser(userFound);
    wallet.setPin(pinRequest.getPin());
    userFound.setWallet(wallet);
    walletRepository.save(wallet);
    return new ResponseData(HttpStatus.OK, "Success", userFound);
  }

  @Override
  public ResponseData verifPin(PinRequest pinRequest, User user) {
    Wallet wallet = walletRepository.findByUserId(user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Wallet", "id", user.getId()));
    WalletValidator.isValidPin(wallet, pinRequest.getPin());
    return new ResponseData(HttpStatus.OK, "Success", true);
  }

}
