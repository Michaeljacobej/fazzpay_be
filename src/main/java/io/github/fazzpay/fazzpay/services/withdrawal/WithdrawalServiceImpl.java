package io.github.fazzpay.fazzpay.services.withdrawal;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotFoundException;
import io.github.fazzpay.fazzpay.models.Method;
import io.github.fazzpay.fazzpay.models.TransactionStatus;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Wallet;
import io.github.fazzpay.fazzpay.models.Withdrawal;
import io.github.fazzpay.fazzpay.payloads.request.WithdrawalRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.repositories.MethodRepository;
import io.github.fazzpay.fazzpay.repositories.WalletRepository;
import io.github.fazzpay.fazzpay.repositories.WithdrawalRepository;
import io.github.fazzpay.fazzpay.validators.WalletValidator;

@Service
@Transactional
public class WithdrawalServiceImpl implements WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Autowired
    private MethodRepository methodRepository;

    @Autowired
    private WalletRepository walletRepository;

    private Withdrawal withdrawal;
    private ResponseData responseData;

    @Override
    public ResponseData createWithdrawalService(WithdrawalRequest withdrawalRequest, User user) {
        withdrawal = new Withdrawal();
        Wallet wallet = user.getWallet();
        WalletValidator.isValidPin(wallet, withdrawalRequest.getPin());

        Method methodFind = methodRepository.findByName(withdrawalRequest.getMethodName())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Method", "Name", withdrawalRequest.getMethodName()));

        WalletValidator.isEnoughBalance(wallet, withdrawalRequest.getAmount());

        withdrawal.setUser(user);
        withdrawal.setMethod(methodFind);
        withdrawal.setAmount(withdrawalRequest.getAmount());
        withdrawal.setStatus(TransactionStatus.CONFIRMED);
        withdrawal.setCreatedAt(LocalDateTime.now());
        withdrawalRepository.save(withdrawal);

        wallet.setBalance(wallet.getBalance() - withdrawalRequest.getAmount());
        walletRepository.save(wallet);
        responseData = new ResponseData(HttpStatus.OK, "success", withdrawal);

        return responseData;
    }

}
