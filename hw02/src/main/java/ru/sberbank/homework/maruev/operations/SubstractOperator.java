package ru.sberbank.homework.maruev.operations;

/**
 * Created by Иван on 15.02.2018.
 */

public class SubstractOperator implements Operator {
    @Override
    public Double getResult(double one, double two) {
        return one - two;
    }
}
