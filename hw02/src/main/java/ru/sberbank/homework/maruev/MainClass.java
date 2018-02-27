package ru.sberbank.homework.maruev;

import java.util.Scanner;

/**
 * Created by Иван on 15.02.2018.
 */
public class MainClass {
    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение, или quit для выхода");
        String input = scanner.nextLine();

        while (!input.equals("quit")) {
            System.out.println(calculator.calculate(input));
            input = scanner.nextLine();
        }
    }
}
