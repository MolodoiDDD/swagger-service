package com.example.swagger_service.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        log.error("MethodArgumentNotValidException", ex);

        String message = "Ошибка валидации";

        if (ex.getBindingResult() != null && !ex.getBindingResult().getFieldErrors().isEmpty()) {
            String field = ex.getBindingResult().getFieldErrors().get(0).getField();
            String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

            message = "Поле '" + field + "': " + errorMessage;
         }

        return ResponseEntity.status(422).body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex)
    {
        log.error("Exception", ex);
        return ResponseEntity.status(500).body("Внутреняня ошибка сервера");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex)
    {
        log.error("EntityNotFoundException", ex);
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex)
    {
        log.error("IllegalArgumentException", ex);
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(CitizenAlreadyExitsException.class)
    public ResponseEntity<String> handleCitizenAlreadyExistException(CitizenAlreadyExitsException ex)
    {
        log.error("CitizenAlreadyExitsException", ex);
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException", ex);
        return ResponseEntity
                .status(422)
                .body("Дата должна быть в формате dd.MM.yyyy");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex)
    {
        if ("birthDate".equals(ex.getName()))
        {
            return ResponseEntity.status(422).body("Дата должна быть в формате dd.MM.yyyy");
        }
        else
        {
            return ResponseEntity.status(422).body("Некорректное значение параметра '" + ex.getMessage() + "'");
        }
    }

}
