package com.example.testservice.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/*
 * Created by Aleksei Vekovshinin on 26.11.2020
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return new ResponseEntity<>(new NotValidException(ex.getBindingResult().getFieldErrors()), HttpStatus.BAD_REQUEST);
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class NotValidException {

        private String object;
        private String field;
        private Object rejectedValue;
        private String message;

        public NotValidException (List<FieldError> errors) {
            FieldError fieldError = errors.get(0);
            this.object = fieldError.getObjectName();
            this.field = fieldError.getField();
            this.rejectedValue = fieldError.getRejectedValue();
            this.message = fieldError.getDefaultMessage();
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


}
