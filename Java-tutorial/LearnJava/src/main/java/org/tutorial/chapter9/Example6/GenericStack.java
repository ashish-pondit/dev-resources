package org.tutorial.chapter9.Example6;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.StringJoiner;

public class GenericStack<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] elements;
    private int count;

    public GenericStack(){
        elements = (T[]) new Object[DEFAULT_CAPACITY];
    }

    private boolean isFull(){
        return elements.length == count;
    }

    private void grow(){
        int newCapacity = elements.length * 2;
        System.out.println("Growing array, new capacity "+newCapacity);
        elements = Arrays.copyOf(elements, newCapacity);
    }

    public void push(T item){
        if(isFull()){
            grow();
        }

        elements[count++] = item;
    }

    public boolean isEmpty(){
        return count == 0;
    }

    public int size(){
        return count;
    }

    public T pop(){
        if(isEmpty()){
            throw new EmptyStackException();
        }

        return elements[--count];
    }

    public void printElements(){
        StringJoiner joiner = new StringJoiner(",", "[ ", "] ");
        for(int i = 0;i<count;i++){
            joiner.add(String.valueOf(elements[i]));
        }

        System.out.println(joiner);
    }


}
