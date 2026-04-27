package ru.gorlov.core.exceptions;

public class UserWithSuchDataAlreadyExistsException extends RuntimeException {
    public UserWithSuchDataAlreadyExistsException(String message) {
        super((message));
    }
}
