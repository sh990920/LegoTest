package com.yanolja.clone.lego.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNoAuthenticationException extends AuthenticationException {
    public UserNoAuthenticationException(String msg){
        super(msg);
    }

    public UserNoAuthenticationException(String msg, Throwable cause){
        super(msg, cause);
    }
}
