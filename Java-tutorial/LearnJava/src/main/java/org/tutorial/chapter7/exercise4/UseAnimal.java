package org.tutorial.chapter7.exercise4;

public class UseAnimal {
    public void doSomething(Animal animal){
        System.out.println("Inside animal version");
    }

    public void doSomething(Horse animal){
        System.out.println("Inside horse version");
    }

    public static void main(String[] args){
        Animal animal = new Horse();
        UseAnimal useAnimal = new UseAnimal();
        useAnimal.doSomething(animal);
    }
}
