package io.github.fazzpay.fazzpay.services.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotFoundException;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Phone;
import io.github.fazzpay.fazzpay.payloads.request.ChangePasswordRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;
import io.github.fazzpay.fazzpay.payloads.response.UserResponse;
import io.github.fazzpay.fazzpay.repositories.PhoneRepository;
import io.github.fazzpay.fazzpay.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PhoneRepository userPhoneRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public ResponseData changePassword(ChangePasswordRequest changePasswordRequest, User user) {
    User userFound = userRepository.findById(user.getId())
        .orElseThrow(() -> new ResourceNotFoundException(null, null, user));
    if (!passwordEncoder.matches(changePasswordRequest.getPassword(), userFound.getPassword())) {
      throw new BadCredentialsException("Bad Credentials");
    }
    userFound.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
    userRepository.save(userFound);
    return new ResponseData(HttpStatus.OK, "Success", userFound);
  }

  @Override
  public ResponseData getAccount(User user) {
    List<Phone> userPhone = userPhoneRepository.findAllByUserId(user.getId());
    UserResponse responseUser = new UserResponse();
    responseUser.setId(user.getId());
    responseUser.setEmail(user.getEmail());
    responseUser.setName(user.getAccount().getName());
    responseUser.setImage(user.getAccount().getImage());
    responseUser.setPhone(userPhone);
    return new ResponseData(HttpStatus.OK, "Auth Success", responseUser);
  }
}
