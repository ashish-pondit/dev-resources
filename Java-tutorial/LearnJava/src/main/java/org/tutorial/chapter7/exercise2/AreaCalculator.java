package org.tutorial.chapter7.exercise2;

public class AreaCalculator {
    // circle, triangle, rectangle, cylinder
//    private double radius;
//    private double armA, armB, armC;
//    private double base, height;
//    private double area;
    public final double PI=3.1416;
    public double getArea(double radius){
        return PI * radius * radius;
    }

    public double getArea(int a, int b, int c){
        double s = (a+b+c) / 2.0;
        return Math.sqrt(s*(s-a)*(s-b)*(s-c)) ;
    }

    public double getArea(int width, int height){
        return (double) width * height;
    }

    public double getArea(double radius, double height){
        return 2 * PI * radius * height + 2 * PI * radius * radius;
    }




}
