package org.tutorial.chapter9.Example6;

public class GenericStackDemo {
    public static void main(String[] args){
        GenericStack<String> cities = new GenericStack<>();
        cities.push("Dhaka");
        cities.push("Thimpu");
        cities.push("Tokyo");
        cities.push("Toronto");
        cities.push("Calgary");
        cities.push("New york");
        cities.push("Kolkata");
        cities.push("Mumbai");
        cities.push("Kualalampur");
        cities.push("Riyad");
        cities.push("Chittagong");
        cities.push("Rangpur");
        cities.printElements();
        cities.pop();
        cities.printElements();
        cities.pop();
        cities.pop();
        cities.printElements();
    }
}
