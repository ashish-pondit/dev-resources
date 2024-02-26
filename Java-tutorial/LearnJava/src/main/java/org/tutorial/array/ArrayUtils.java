package org.tutorial.array;

import org.tutorial.Main;

public class ArrayUtils {
    public static int[] getRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            int randomNumber = (int) (Math.random() * 100) % 100;
            arr[i] = randomNumber;
        }

        return arr;
    }
}
