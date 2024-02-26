package org.tutorial.chapter10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StandardIOExample {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Start typing: ");
        String line;

        do{
            line = reader.readLine();
            System.out.println(">> "+line.toUpperCase());
        }while (!"quit".equals(line));
    }
}
