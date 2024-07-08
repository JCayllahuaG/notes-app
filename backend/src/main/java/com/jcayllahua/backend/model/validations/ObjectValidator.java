package com.jcayllahua.backend.model.validations;


import com.jcayllahua.backend.model.exceptions.CustomException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class ObjectValidator {
    private final Validator validator;

    public ObjectValidator(Validator validator) {
        this.validator = validator;
    }


    @SneakyThrows
    public <T> T validate(T object){
        Set<ConstraintViolation<T>> errors = validator.validate(object);
        if(errors.isEmpty())
            return object;
        else {
            String message = errors.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new CustomException(HttpStatus.BAD_REQUEST, message);
        }
    }
}
