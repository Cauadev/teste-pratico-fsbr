package com.cauadev.teste_pratico_fsbr.infra.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
