package io.github.fazzpay.fazzpay.services.account;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotFoundException;
import io.github.fazzpay.fazzpay.models.Account;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.ChangeNameRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.repositories.AccountRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Value("${fazzpay.app.image-directory}")
    private String IMAGE_DIR;

    @Override
    public ResponseData changeName(ChangeNameRequest changeNameRequest, User user) {
        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", user.getId()));
        account.setName(changeNameRequest.getName());
        accountRepository.save(account);
        return new ResponseData(HttpStatus.OK, "Success", user);
    }

    @Override
    public ResponseData uploadImage(MultipartFile multipartFile, User user) throws IOException {
        String filename = UUID.randomUUID().toString();
        Path uploadPath = Paths.get(IMAGE_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream = multipartFile.getInputStream();
        Path filePath = uploadPath.resolve(filename);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", user.getId()));
        account.setImage(filename);
        Account savedAccount = accountRepository.save(account);

        return new ResponseData(HttpStatus.OK, "Success", savedAccount);
    }

}
