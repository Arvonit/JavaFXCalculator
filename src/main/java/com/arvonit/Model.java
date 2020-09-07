package com.arvonit;

import java.util.function.DoubleBinaryOperator;

public class Model {

    public enum Operation {
        ADD("+", (d1, d2) -> d1 + d2),
        SUBTRACT("-", (d1, d2) -> d1 - d2),
        MULTIPLY("x", (d1, d2) -> d1 * d2),
        DIVIDE("/", (d1, d2) -> d1 / d2);

        private final String symbol;
        private final DoubleBinaryOperator operation;

        Operation(String symbol, DoubleBinaryOperator operation) {
            this.symbol = symbol;
            this.operation = operation;
        }

        public double calculate(double firstNumber, double secondNumber) {
            return operation.applyAsDouble(firstNumber, secondNumber);
        }

        public static Operation getFromSymbol(String symbol) {
            switch (symbol) {
                case "+":
                    return ADD;
                case "-":
                    return SUBTRACT;
                case "x":
                    return MULTIPLY;
                case "/":
                    return DIVIDE;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public double calculate(double firstNumber, double secondNumber, Operation operation) {
        return operation.calculate(firstNumber, secondNumber);
    }

}
