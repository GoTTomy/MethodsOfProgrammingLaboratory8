package com.example.demo.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class StudentExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {StudentNotFoundException.class, NoSuchElementException.class})
    protected ResponseEntity<Object> StudentNotFoundException(RuntimeException e, WebRequest request){
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
