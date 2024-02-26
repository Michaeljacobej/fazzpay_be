package io.github.fazzpay.fazzpay.services.Method;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.fazzpay.fazzpay.models.Method;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.repositories.MethodRepository;

@Service
@Transactional
public class MethodServiceImpl implements MethodService {
    @Autowired
    private MethodRepository methodRepository;

    private ResponseData responseData;
    private List<Method> methods;

    @Override
    public ResponseData readAllMethod() {
        methods = methodRepository.findAll();
        responseData = new ResponseData(HttpStatus.OK, "success", methods);

        return responseData;
    }

}
