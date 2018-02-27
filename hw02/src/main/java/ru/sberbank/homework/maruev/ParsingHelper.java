package ru.sberbank.homework.maruev;

import ru.sberbank.homework.maruev.exceptions.InvalidOperandException;
import ru.sberbank.homework.maruev.exceptions.InvalidOperatorException;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Иван on 24.02.2018.
 */
public class ParsingHelper {
    public Deque<String> operatorStack = new ArrayDeque<>();
    public Deque<String> operandStack = new ArrayDeque<>();
    public Deque<Double> operandDeque = new ArrayDeque<>();
    public double currentOperand;

    public Deque<Double> firstParse(String[] inputMass) {
        operandStack.addFirst(inputMass[0]);
        operandStack.addLast(inputMass[2]);
        operatorStack.add(inputMass[1]);

        if (isOperand(operandStack.peekFirst())) {
            operandStack.pollFirst();
            operandDeque.addFirst(currentOperand);
        } else {
            throw new InvalidOperandException(operandStack.pollFirst());
        }

        if (isOperand((operandStack.peekLast()))) {
            operandStack.pollLast();
            operandDeque.addLast(currentOperand);
        } else {
            throw new InvalidOperandException(operandStack.pollLast());
        }

        if (isOperator(operatorStack.peek())) {

        } else {
            throw new InvalidOperatorException(operatorStack.poll());
        }
        return operandDeque;
    }

    public Deque<Double> nextParse(String[] input) {
        operatorStack.add(input[0]);
        operandStack.addFirst(input[1]);

        if (isOperator(operatorStack.peek())) {

        } else {
            throw new InvalidOperatorException(operatorStack.poll());
        }

        if (isOperand((operandStack.peekFirst()))) {
            operandStack.pollFirst();
            operandDeque.addFirst(currentOperand);
        } else {
            throw new InvalidOperandException(operandStack.poll());
        }
        return operandDeque;
    }

    public boolean isOperand(String operand) {
        operand = operand.toLowerCase();
        if (operand.startsWith("_")
                || operand.endsWith("_")
                || operand.contains("._") || operand.contains("_.")
                || operand.contains("_l") || operand.contains("0b_")
                || operand.contains("0x_")) {
            return false;
        }

        if (operand.endsWith("l")) {
            operand = operand.replaceAll("l", "");
        }

        if (operand.startsWith("0x") || operand.startsWith("-0x") || operand.startsWith("+0x")) {
            return changeOperand(operand, "0x", "_", 16);
        } else if (operand.startsWith("0b") || operand.startsWith("-0b") || operand.startsWith("+0b")) {
            return changeOperand(operand, "0b", "_", 2);
        } else if ((operand.startsWith("0") || operand.startsWith("-0") || operand.startsWith("+0")) && !operand.contains(".")) {
            return changeOperand(operand, "", "_", 8);
        } else {
            try {
                operand = removeAll(operand, "", "_");
                currentOperand = Double.parseDouble(operand);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public boolean isOperator(String input) {
        if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/")) {
            return true;
        } else {
            return false;
        }
    }

    private String removeAll(String operand, String suffix, String symbol) {
        operand = operand.replace(symbol, "").replace(suffix, "");
        return operand;
    }

    private boolean changeOperand(String operand, String suffix, String symbol, int radix) {
        operand = removeAll(operand, suffix, symbol);
        try {
            currentOperand = Long.parseLong(operand, radix);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
