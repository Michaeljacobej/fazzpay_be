package io.github.fazzpay.fazzpay.services.account;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.payloads.request.ChangeNameRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

public interface AccountService {

    public ResponseData changeName(ChangeNameRequest changeNameRequest, User user);

    public ResponseData uploadImage(MultipartFile multipartFile, User user) throws IOException;

}
