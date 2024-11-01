package com.lucasAmbrosio.WebShelfProject.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundInDbException extends RuntimeException{
    public NotFoundInDbException(String message){
        super(message);
    }
}
