package com.mdkj.exception;

public class TokenExpiredSoonException extends RuntimeException {
    public TokenExpiredSoonException(String newToken) {
        super(newToken);
    }
}