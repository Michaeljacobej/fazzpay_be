package io.github.fazzpay.fazzpay.services.deposit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Wallet;
import io.github.fazzpay.fazzpay.models.Deposit;
import io.github.fazzpay.fazzpay.models.TransactionStatus;
import io.github.fazzpay.fazzpay.repositories.DepositRepository;
import jakarta.transaction.Transactional;
import io.github.fazzpay.fazzpay.repositories.WalletRepository;
import io.github.fazzpay.fazzpay.payloads.request.DepositRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

@Service
@Transactional
public class DepositServiceImpl implements DepositService {

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private WalletRepository walletRepository;

    private Deposit deposit;

    @Override
    public ResponseData createDeposit(DepositRequest request, User user) {
        // User userFound = userRepository.findById(user.getId()).orElseThrow();

        // get user wallet
        Wallet wallet = walletRepository.findByUserId(user.getId()).orElseThrow();

        // add new deposit
        deposit = new Deposit();

        deposit.setUser(user);
        deposit.setAmount(request.getAmount());
        deposit.setStatus(TransactionStatus.CONFIRMED);

        // add deposit amount to wallet balance
        wallet.setBalance(wallet.getBalance() + request.getAmount());
        walletRepository.save(wallet);

        // save deposit
        depositRepository.save(deposit);

        // Response Data
        return new ResponseData(HttpStatus.OK, "Success", deposit);
    }

    @Override
    public ResponseData readDeposit(Boolean status) {
        List<Deposit> deposits = depositRepository.findAll();
        return new ResponseData(HttpStatus.OK, "Success", deposits);
    }

}