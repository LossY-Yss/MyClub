package com.mdkj.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String coderMessage) {
        super(coderMessage);
    }
}