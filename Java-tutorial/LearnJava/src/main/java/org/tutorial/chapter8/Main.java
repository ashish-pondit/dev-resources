package org.tutorial.chapter8;

public class Main {

    public static void exercise3(int a, int b){
        if(a<0 || b<0){
            throw new IllegalArgumentException("Value must be greater then zero");
        }
        System.out.println("Parameters are "+a+","+b);
    }
    public static void main(String[] args){
        System.out.println("Hello world!");

        try {
            exercise3(5,6);
            exercise3(6,-5);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
