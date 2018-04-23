package com.hfkj.bbt.exception;

import org.springframework.security.core.AuthenticationException;


public class MyUserNameNotFoundException extends AuthenticationException {
    public MyUserNameNotFoundException(String msg) {
        super(msg);
    }
}
