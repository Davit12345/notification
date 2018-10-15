package com.example.demo.model.exceptions;

import com.example.demo.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,reason = Constants.ERROR_MESSAGE)
public class InternalErrorException extends Exception {

    public InternalErrorException(String message) {
        super(message);
    }


    public InternalErrorException() {
        super();
    }

}
