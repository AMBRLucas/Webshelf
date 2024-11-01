package com.lucasAmbrosio.WebShelfProject.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ActiveLoanException extends RuntimeException {
    public ActiveLoanException(String message){
        super(message);
    }
}
