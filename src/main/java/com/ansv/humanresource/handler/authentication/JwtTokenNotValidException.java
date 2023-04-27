package com.ansv.humanresource.handler.authentication;

import javax.naming.AuthenticationException;

public class JwtTokenNotValidException extends AuthenticationException {

    public JwtTokenNotValidException(String msg) {
        super(msg);
    }
}
