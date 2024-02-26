package org.tutorial.chapter9.Example3;

public class DynamicArrayDemo {
    public static void main(String[] args){
        DynamicArray<Integer> numbers = new DynamicArray<>();
        int value = 55;
        for(int i=0;i<50;i++){
            numbers.add(value++);
        }

        System.out.println(numbers);

        // accessng by index
        System.out.println(numbers.get(0));
        System.out.println(numbers.get(30));
        System.out.println(numbers.get(12));

        System.out.println("Total elements = "+ numbers.size());

        DynamicArray<String> cities = new DynamicArray<>();
        cities.add("Dhaka");
        cities.add("Barisal");
        cities.add("Chittagong");
        cities.add("Kishoreganj");
        cities.add("Narayanganj");
        cities.add("New york");
        cities.add("Tokyo");

        System.out.println(cities);


    }
}
