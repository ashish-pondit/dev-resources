package org.tutorial.chapter9.Example5;

import java.util.ArrayList;
import java.util.List;

public class WildCardArgument {
    public static void printList(List<?> list){
        System.out.println("Printing list:");
        for(Object o:list){
            System.out.print(o+" ");
        }
        System.out.println();
    }

    public static void upperBoundedWildcard(List<? extends UserDefineClass> list){
        System.out.println("Only print user define class or its subclass");
        printList(list);
    }

    public static void lowerBoundedWildCard(List<? super Integer> list){
        System.out.println("only print Integer or its superclass");
        printList(list);
    }
    public static void main(String[] args){
        // Example 1
        List<Object> objList = new ArrayList<>();
        objList.add(Integer.valueOf("55"));
        objList.add(66);
        printList(objList);

        // Example
        List<String> strList = new ArrayList<String>();
        strList.add("one");
        strList.add(String.valueOf('g'));
        printList(strList);


        List<SubClassType> testList = new ArrayList<>();
        testList.add(new SubClassType(55));
        testList.add(new SubClassType(66));

        upperBoundedWildcard(testList);

        lowerBoundedWildCard(objList);

    }
}
