package com.api.Loja.helpers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {

        var errors = ex.getFieldErrors().stream().
                map(fe -> Map.of("mensagem", fe.getDefaultMessage())).toList();
        var body = Map.of(
                "status", 400,
                "erro", "Requisição Inválida",
                "erros", errors
        );
        return ResponseEntity.badRequest().body(body);

    }

}
