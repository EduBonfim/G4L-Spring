package com.jogosparavida.games4life_backend.exception;


/*
 * ApiExceptionHandler
 * --------------------------------------------
 * Classe responsável por centralizar o tratamento
 * de erros da aplicação usando @ControllerAdvice.
 *
 * - Intercepta ResponseStatusException e retorna
 *   o status e motivo definidos.
 *
 * - Captura exceções genéricas e devolve
 *   BAD_REQUEST com a mensagem do erro.
 *
 * Mantém respostas padronizadas e evita
 * repetição de tratamento nos controllers.
 * --------------------------------------------
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatus(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
