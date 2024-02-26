package org.tutorial.chapter9.Exercise1;

public class CalculatorDemo {
    public static void main(String[] args){
        Calculator<Integer, Float> calc = new Calculator<>(55, 66.0f, "%");
        System.out.println(calc);
    }
}
