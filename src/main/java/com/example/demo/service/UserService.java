package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.exceptions.DuplicateDataException;
import com.example.demo.model.exceptions.InternalErrorException;
import com.example.demo.model.exceptions.NotFoundException;
import com.example.demo.model.exceptions.TeapotException;

public interface UserService {


    void add(User user) throws DuplicateDataException, InternalErrorException;

    void verify(String email,String verificationCode) throws InternalErrorException, TeapotException;

    void sendRecoveringCode(String email) throws InternalErrorException;

    void changePassword(String email,String password,String recoveringCode) throws InternalErrorException;

    void resendVerificationCode(String email) throws NotFoundException, InternalErrorException;

    User getByEmail(String email) ;
}
