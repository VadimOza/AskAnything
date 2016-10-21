package com.askanything.web.controllers;

import com.askanything.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by root on 21.10.16.
 */

@ControllerAdvice
public class ContriollerAdviceException {

    @ExceptionHandler(UserNotFoundException.class)
    public String notFoundException(){
        return "error/user-not-found";
    }
}
