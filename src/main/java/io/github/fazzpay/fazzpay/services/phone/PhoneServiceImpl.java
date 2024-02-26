package io.github.fazzpay.fazzpay.services.phone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceAlreadyExistException;
import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotFoundException;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Phone;
import io.github.fazzpay.fazzpay.payloads.request.PhoneRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.repositories.PhoneRepository;
import io.github.fazzpay.fazzpay.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseData createUserPhone(PhoneRequest request, User user) {
        phoneRepository.findByUserIdAndPhone(user.getId(), request.getPhone()).ifPresent(t -> {
            throw new ResourceAlreadyExistException("Phone", "phone number", request.getPhone());
        });

        User userFound = userRepository.findById(user.getId()).orElseThrow();

        Phone userPhone = new Phone();

        userPhone.setPhone(request.getPhone());
        userPhone.setUser(userFound);

        phoneRepository.findByUserIdAndIsPrimaryTrue(user.getId()).ifPresent((t) -> {
            userPhone.setIsPrimary(false);
        });

        phoneRepository.save(userPhone);
        return new ResponseData(HttpStatus.OK, "Success", userPhone);
    }

    @Override
    public ResponseData makePhonePrimary(PhoneRequest request, User user) {
        Phone userPhone = phoneRepository.findByUserIdAndPhone(user.getId(), request.getPhone())
                .orElseThrow(() -> new ResourceNotFoundException("User Phone", "Phone Number", request.getPhone()));

        phoneRepository.findByUserIdAndIsPrimaryTrue(user.getId()).ifPresent((t) -> {
            t.setIsPrimary(false);
        });
        userPhone.setIsPrimary(true);

        return new ResponseData(HttpStatus.OK, "Success", null);
    }

    @Override
    public ResponseData deleteUserPhone(PhoneRequest request, User user) {
        Phone phone = phoneRepository.findByUserIdAndPhone(user.getId(), request.getPhone())
                .orElseThrow(() -> new ResourceNotFoundException("User Phone", "Phone Number", request.getPhone()));

        if (phone.getIsPrimary()) {
            phoneRepository.findFirstByUserId(user.getId()).ifPresent(t -> {
                t.setIsPrimary(true);
                phoneRepository.save(t);
            });
        }

        phoneRepository.delete(phone);

        return new ResponseData(HttpStatus.OK, "Deleted", null);
    }

    @Override
    public ResponseData getUserPhone(User user) {
        List<Phone> allListPhone = phoneRepository.findAllByUserId(user.getId());
        return new ResponseData(HttpStatus.OK, "Success", allListPhone);

    }

}
