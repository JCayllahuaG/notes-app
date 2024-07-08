package com.jcayllahua.backend.model.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException  extends Exception{
    private final HttpStatus httpStatus;
    public CustomException( HttpStatus httpStatus,String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
