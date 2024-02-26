package org.tutorial.chapter7.exercise3;

public class TestMain {
    public static void main(String[] args){
        Author author1 = new Author("Bazlur Rahman", "bazlur@gmail.com", "male");
        Book book1 = new Book("Java Programming", author1, 850, 5);

        System.out.println("Showing new book information");
        System.out.println(book1.toString());


    }
}
