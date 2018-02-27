
package ru.sberbank.homework.maruev;

/**
 * Используется в классе HardCalculator
 */
public enum Operation {
    SUM("+"),
    SUBSTRACT("-"),
    MULTIPLY("*"),
    DIVISION("/");

    private final String OPERATOR;

    Operation(String OPERATOR) {
        this.OPERATOR = OPERATOR;

    }

    public String getOperation() {
        return OPERATOR;
    }
}
