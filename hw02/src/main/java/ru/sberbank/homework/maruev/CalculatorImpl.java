package ru.sberbank.homework.maruev;

import ru.sberbank.homework.common.Calculator;
import ru.sberbank.homework.maruev.exceptions.InvalidExpression;
import ru.sberbank.homework.maruev.exceptions.InvalidOperandException;
import ru.sberbank.homework.maruev.exceptions.InvalidOperatorException;
import ru.sberbank.homework.maruev.operations.*;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Иван on 26.02.2018.
 */
public class CalculatorImpl implements Calculator {
    private final static String MESSAGE_ERROR = "error > ";
    private final static String DIVISION_ERROR = "Division by zero";
    private final static String WR_EXPRESSION = "wrong expression";
    private Operator operator;
    public Deque<Double> operands = new ArrayDeque<>();
    public Deque<String> operation = new ArrayDeque<>();
    public Deque<Double> result = new ArrayDeque<>();
    public ParsingHelper parser = new ParsingHelper();

    @Override
    public String calculate(String userInput) {
        try {
            addDeques(userInput);
            initializeOperator();

            if (result.isEmpty() && operands.size() == 2) {
                result.push(operator.getResult(operands.pollFirst(), operands.pollLast()));
            } else if (!result.isEmpty() && operands.size() == 1) {
                result.push(operator.getResult(result.poll(), operands.pollLast()));
            } else {
                throw new InvalidExpression();
            }
            return new DecimalFormat("#.##").format(result.peek()).replaceAll(",", ".");

        } catch (InvalidExpression e) {
            return MESSAGE_ERROR + WR_EXPRESSION;
        } catch (InvalidOperatorException e) {
            return MESSAGE_ERROR + e.getMessage();
        } catch (InvalidOperandException e) {
            return MESSAGE_ERROR + e.getMessage();
        } catch (ArithmeticException e) {
            return MESSAGE_ERROR + DIVISION_ERROR;
        }
    }

    public void addDeques(String input) {
        String[] expression = input.split(" ");

        if (expression.length == 3) {
            clearAll();
            operands = parser.firstParse(expression);
            operation = parser.operatorStack;
        } else if (expression.length == 2 && !result.isEmpty()) {
            operands = parser.nextParse(expression);
            operation = parser.operatorStack;
        } else {
            throw new InvalidExpression();
        }
    }

    public void initializeOperator() {
        if (operation.peekLast().equals(Operation.SUM.getOperation())) {
            operator = new SumOperator();
        } else if (operation.peekLast().equals(Operation.SUBSTRACT.getOperation())) {
            operator = new SubstractOperator();
        } else if (operation.peekLast().equals(Operation.MULTIPLY.getOperation())) {
            operator = new MultiplyOperator();
        } else if (operation.peekLast().equals(Operation.DIVISION.getOperation())) {
            operator = new DivisionOperator();
        } else {
            throw new InvalidOperatorException();
        }
    }

    public void clearAll() {
        //operands.clear();
        //operation.clear();
        result.clear();
    }
}
