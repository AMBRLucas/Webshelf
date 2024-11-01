package com.lucasAmbrosio.WebShelfProject.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RelatedDataException extends RuntimeException {
    public RelatedDataException(String message){
        super(message);
    }
}
