package org.tutorial.chapter7.exercise2;

public class AreaTest {
    public static void main(String[] args){
        AreaCalculator areaCalculator = new AreaCalculator();

        System.out.println("Area of circle is "+areaCalculator.getArea(5));
        System.out.println("Area of triangle is "+areaCalculator.getArea(5,7,8));
        System.out.println("Area of circle is "+areaCalculator.getArea(5,8));
        System.out.println("Area of circle is "+areaCalculator.getArea(6f,7f));
    }
}
