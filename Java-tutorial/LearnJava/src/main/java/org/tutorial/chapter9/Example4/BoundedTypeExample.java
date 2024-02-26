package org.tutorial.chapter9.Example4;

public class BoundedTypeExample<T extends Number> {
    /* Example of bounded type. This class will return the average of some numbers.
     */

    private T[] numbers;

    public BoundedTypeExample(T[] numbers){
        this.numbers = numbers;
    }

    public double average(){
        double sum = 0.0;
        for(T number:numbers){
            sum += number.doubleValue();
        }
        return sum / numbers.length;
    }

}
