package org.tutorial.chapter9.Example4;

import java.util.Arrays;

public class BoundedTypeDemo {
    public static void main(String[] args){
        Integer[] arr = {5,4,3,2,7,8,9};
        BoundedTypeExample<Integer> stat = new BoundedTypeExample<>(arr);
        System.out.println("The array is "+ Arrays.toString(arr));
        System.out.println("Average is = "+stat.average());
    }
}
