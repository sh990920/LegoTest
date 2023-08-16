package com.yanolja.clone.lego.exceptions;

import org.springframework.security.core.AuthenticationException;

// 관리자가 허락하지 않은 사업자가 로그인을 하려할 때 사용할 Exception 클래스
public class UserNoAuthenticationException extends AuthenticationException {
    public UserNoAuthenticationException(String msg){
        super(msg);
    }

    public UserNoAuthenticationException(String msg, Throwable cause){
        super(msg, cause);
    }
}
