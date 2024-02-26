package org.tutorial.chapter9.Example1;

public class GenericClassDemo {
    public static void main(String[] args){
        Generic<Integer> iObj;
        iObj = new Generic<>(88);
        iObj.showType();

        int value = iObj.getObj();

        System.out.println("Value = "+value);

        // Example
        Generic<String> strObj = new Generic<>("Hello world");
        strObj.showType();
        String strValue = strObj.getObj();
        System.out.println("Str value = "+ strValue);

    }
}
