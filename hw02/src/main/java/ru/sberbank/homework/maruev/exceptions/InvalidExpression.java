package ru.sberbank.homework.maruev.exceptions;

/**
 * Created by Иван on 26.02.2018.
 */
public class InvalidExpression extends RuntimeException {
    public InvalidExpression() {
        super();
    }

    public InvalidExpression(String message) {
        super(message);
    }
}
