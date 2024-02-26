package io.github.fazzpay.fazzpay.services.contact;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import io.github.fazzpay.fazzpay.exceptions.custom.ResourceAlreadyExistException;
import io.github.fazzpay.fazzpay.exceptions.custom.ResourceNotFoundException;
import io.github.fazzpay.fazzpay.models.User;
import io.github.fazzpay.fazzpay.models.Contact;
import io.github.fazzpay.fazzpay.repositories.UserRepository;
import io.github.fazzpay.fazzpay.repositories.ContactRepository;
import io.github.fazzpay.fazzpay.repositories.PhoneRepository;
import jakarta.transaction.Transactional;
import io.github.fazzpay.fazzpay.payloads.request.ContactRequest;
import io.github.fazzpay.fazzpay.payloads.response.ResponseData;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PhoneRepository userPhoneRepository;

    @Override
    public ResponseData addUserContact(ContactRequest request, User user) {
        if (user.getId().equals(request.getContactId())) {
            throw new RuntimeException("Cannot add yourself to your contact");
        }
        if (contactRepository.existsByUserIdAndContactId(user.getId(), request.getContactId())) {
            throw new ResourceAlreadyExistException("Contact", "id", request.getContactId());
        }

        User userFound = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
        User contact = userRepository.findById(request.getContactId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
        Contact userContact = new Contact();

        userContact.setUser(userFound);
        userContact.setContact(contact);

        contactRepository.save(userContact);
        return new ResponseData(HttpStatus.OK, "Success", userContact);
    }

    @Override
    public ResponseData readUserContact(User user) {
        List<Contact> userContacts = contactRepository.findByUserId(user.getId());
        List<User> users = userContacts.stream().map(t -> t.getContact()).toList();
        return new ResponseData(HttpStatus.OK, "Success", users);
    }

    @Override
    public ResponseData deleteUserContact(Long id) {
        Contact userContactFind = contactRepository.findById(id).orElseThrow();

        contactRepository.delete(userContactFind);
        return new ResponseData(HttpStatus.OK, "Success", null);
    }

    @Override
    public ResponseData search(String search, User user) {
        List<User> users = new ArrayList<>();
        userRepository.findByEmailAndIdIsNot(search, user.getId()).ifPresent(t -> {
            users.add(t);
        });
        userPhoneRepository.findByPhone(search).ifPresent(t -> {
            users.add(t.getUser());
        });
        return new ResponseData(HttpStatus.OK, "Success", users);
    }

}
