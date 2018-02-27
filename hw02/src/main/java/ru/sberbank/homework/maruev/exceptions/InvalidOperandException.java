package ru.sberbank.homework.maruev.exceptions;

/**
 * Created by Иван on 26.02.2018.
 */
public class InvalidOperandException extends RuntimeException {
    public InvalidOperandException() {
        super();
    }

    public InvalidOperandException(String message) {
        super(message);
    }
}
