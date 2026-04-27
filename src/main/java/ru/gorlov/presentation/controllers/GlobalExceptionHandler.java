package ru.gorlov.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gorlov.core.exceptions.BadAuthDataException;
import ru.gorlov.core.exceptions.EntityNotFoundException;
import ru.gorlov.core.exceptions.IncompatibleComponentException;
import ru.gorlov.core.exceptions.IncompatibleRoleException;
import ru.gorlov.core.exceptions.UserWithSuchDataAlreadyExistsException;
import ru.gorlov.presentation.dto.error.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IncompatibleComponentException.class)
    public ResponseEntity<ErrorResponse> handleIncompatibleComponent(
        IncompatibleComponentException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(IncompatibleRoleException.class)
    public ResponseEntity<ErrorResponse> handleIncompatibleRole(
        IncompatibleRoleException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArguments(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserWithSuchDataAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserWithSuchDataIsAlreadyExists(
        UserWithSuchDataAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(BadAuthDataException.class)
    public ResponseEntity<ErrorResponse> handleBadAuthData(
        BadAuthDataException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
