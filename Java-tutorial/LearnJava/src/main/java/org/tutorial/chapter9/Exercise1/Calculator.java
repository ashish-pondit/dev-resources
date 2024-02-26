package org.tutorial.chapter9.Exercise1;

public class Calculator<T extends Number, U extends Number> {
    private final T operand1;
    private final U operand2;
    private final String operator;

    public Calculator(T operand1, U operand2, String operator){
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public double calculate(){
        double result = switch (operator){
            case "+" -> operand1.doubleValue() + operand2.doubleValue();
            case "-" -> operand1.doubleValue() - operand2.doubleValue();
            case "*" -> operand1.doubleValue() * operand2.doubleValue();
            case "/" -> operand1.doubleValue() / operand2.doubleValue();
            case "%" -> operand1.doubleValue() % operand2.doubleValue();
            default -> 0;
        };
        return result;
    }

    @Override
    public String toString() {
        return operand1 + " " + operator + " " + operand2+" = " + calculate();
    }
}
