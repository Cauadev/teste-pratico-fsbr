package com.cauadev.teste_pratico_fsbr.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMethodValidException(MethodArgumentNotValidException argumentNotValidException) {

        List<String> errors = argumentNotValidException.getBindingResult().getAllErrors().stream()
                .map(err -> err.getDefaultMessage()).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new ApiError(errors));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException businessException) {
        return ResponseEntity.badRequest().body(new ApiError(businessException.getMessage()));
    }

}
