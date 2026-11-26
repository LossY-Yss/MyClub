package com.mdkj.exception;

public class IllegalParamException extends RuntimeException {
    public IllegalParamException(String coderMessage) {
        super(coderMessage);
    }
}