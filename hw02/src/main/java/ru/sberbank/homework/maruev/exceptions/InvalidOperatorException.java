package ru.sberbank.homework.maruev.exceptions;

/**
 * Created by Иван on 26.02.2018.
 */
public class InvalidOperatorException extends RuntimeException {
    public InvalidOperatorException() {
        super();
    }

    public InvalidOperatorException(String message) {
        super(message);
    }
}
