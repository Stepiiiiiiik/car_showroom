package ru.gorlov.core.exceptions;

public class BadAuthDataException extends RuntimeException {

    public BadAuthDataException(String message) {
        super(message);
    }
}
