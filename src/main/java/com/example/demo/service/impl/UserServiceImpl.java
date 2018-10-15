package com.example.demo.service.impl;

import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.model.enums.Status;
import com.example.demo.model.exceptions.DuplicateDataException;
import com.example.demo.model.exceptions.InternalErrorException;
import com.example.demo.model.exceptions.NotFoundException;
import com.example.demo.model.exceptions.TeapotException;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.Constants;
import com.example.demo.util.MailSenderClient;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.Temporal;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private MailSenderClient mailClient;

    @Transactional
    @Override
    public void add(User user) throws DuplicateDataException, InternalErrorException {

        try {
            User duplicate = userRepository.getByEmail(user.getEmail());
            if (duplicate != null) {
                throw new DuplicateDataException("There is user with this email");
            }

            user.setStatus(Status.Unverified);
            user.setCode(Util.generateRandomChars());
            user.setPassword(Util.getEncoded(user.getPassword()));
            Authority authority = authorityRepository.getByName(Constants.ROLE_USER);
            userRepository.save(user);
            user.setAuthorities(Collections.singleton(authority));
            mailClient.send(user.getEmail(), Constants.EMAIL_SUBJECTS_VERIFICATION, Util.getVerificationMessage(user.getName(), user.getCode()));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new InternalErrorException(Constants.ERROR_MESSAGE);
        }


    }

    @Transactional
    @Override
    public void verify(String email, String verificationCode) throws InternalErrorException, TeapotException {
        try {
            User user = userRepository.getByEmail(email);
            if (user.getStatus().equals(Status.Active)) {
                throw new TeapotException("USer is already verified, Why do you want to verify him?");
            }
            if (verificationCode.equals(user.getCode())) {
                user.setCode(null);
                user.setStatus(Status.Active);
                userRepository.save(user);
            } else {

                throw new IllegalAccessError(Constants.WRONG_REQUEST);
            }
        } catch (RuntimeException e) {
            throw new InternalErrorException(Constants.ERROR_MESSAGE);
        }
    }


    @Override
    public void sendRecoveringCode(String email) throws InternalErrorException {

        try {
            User user = userRepository.getByEmail(email);
            if (user != null) {
                if (user.getStatus() == Status.Active) {
                    user.setCode(Util.generateRandomChars());
                    mailClient.send(user.getEmail(), Constants.EMAIL_SUBJECTS_RECOVERING, Util.getRecoveringMessage(user.getName(), user.getCode()));
                    userRepository.save(user);
                } else {
                    throw new IllegalAccessError("Pleas  pass the  verification");

                }

            } else {

                throw new IllegalAccessError(Constants.WRONG_REQUEST);
            }
        } catch (RuntimeException e) {
            throw new InternalErrorException(Constants.ERROR_MESSAGE);
        }

    }


    @Transactional
    @Override
    public void changePassword(String email, String password, String recoveringCode) throws InternalErrorException {

        try {
            User user = userRepository.getByEmail(email);

            if (recoveringCode.equals(user.getCode())) {
                user.setPassword(Util.getEncoded(password));
                user.setCode(null);
                userRepository.save(user);
            } else {
                throw new IllegalAccessError(Constants.WRONG_REQUEST);
            }
        } catch (RuntimeException e) {
            throw new InternalErrorException(Constants.ERROR_MESSAGE);
        }

    }

    @Transactional
    @Override
    public void resendVerificationCode(String email) throws NotFoundException, InternalErrorException {
        try {
            User user = userRepository.getByEmail(email);
            if (user == null) {
                throw new NotFoundException("There is no User with this email");
            }
            if (user.getStatus() != Status.Unverified) {
                throw new NotFoundException("Not Found");

            }
            user.setCode(Util.generateRandomChars());
            mailClient.send(user.getEmail(), Constants.EMAIL_SUBJECTS_VERIFICATION, Util.getVerificationMessage(user.getName(), user.getCode()));
        } catch (RuntimeException e) {
            throw new InternalErrorException("Something went wrong, please try later");
        }
    }


    @Transactional(readOnly = true)
    @Override
    public User getByEmail(String email) {

        return userRepository.getByEmail(email);
    }
}
