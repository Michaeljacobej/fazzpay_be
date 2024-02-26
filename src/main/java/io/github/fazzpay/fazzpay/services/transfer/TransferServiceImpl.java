package io.github.fazzpay.fazzpay.services.transfer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotEnoughException;
import io.github.fazzpay.fazzpay.exceptions.custom.TransferSelfException;
import io.github.fazzpay.fazzpay.models.Transaction;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Wallet;
import io.github.fazzpay.fazzpay.payloads.request.TransferRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.repositories.TransactionRepository;
import io.github.fazzpay.fazzpay.repositories.UserRepository;
import io.github.fazzpay.fazzpay.repositories.WalletRepository;
import io.github.fazzpay.fazzpay.validators.WalletValidator;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    // Create - add new transfer transaction
    @Override
    public ResponseData createTransferService(TransferRequest request, User user) {

        // cek apakah user id sender = user id receiver
        if (user.getId().equals(request.getTo())) {
            throw new TransferSelfException();
        }

        User userTo = userRepository.findById(request.getTo())
                .orElseThrow(() -> new ResourceNotEnoughException("User", "id", request.getTo()));

        // get wallet sender and receiver
        Wallet wallet = walletRepository.findByUserId(user.getId()).orElseThrow();
        Wallet walletTo = walletRepository.findByUserId(userTo.getId()).orElseThrow();
        WalletValidator.isEnoughBalance(wallet, request.getAmount());

        // add new transfer
        Transaction transfer = new Transaction();
        transfer.setFromUser(user);
        transfer.setToUser(userTo);
        transfer.setAmount(request.getAmount());
        transfer.setNotes(request.getNotes());

        // set balance of each wallet
        wallet.setBalance(wallet.getBalance() - request.getAmount());
        walletTo.setBalance(walletTo.getBalance() + request.getAmount());
        walletRepository.saveAll(List.of(wallet, walletTo));

        // save data
        transactionRepository.save(transfer);

        return new ResponseData(HttpStatus.OK, "Success", transfer);
    }

}
