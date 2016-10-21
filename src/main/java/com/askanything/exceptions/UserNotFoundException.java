package com.askanything.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by root on 21.10.16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Spittle not found!")
public class UserNotFoundException extends RuntimeException {
}
