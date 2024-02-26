package org.tutorial.array;

import java.util.Arrays;
import java.util.Scanner;

public class RotationOfArray {

    public static int[] rotateLeft(int[] arr, int rotation) {
        /*Lazy rotation. It can be optimized*/

        arr = Arrays.copyOf(arr, arr.length);

        for (int r = 0; r < rotation; r++) {
            int tempValue = arr[0];
            for (int i = 0; i < arr.length; i++) {
                if (i == arr.length - 1) {
                    arr[i] = tempValue;
                    continue;
                }

                arr[i] = arr[i + 1];
            }

            System.out.println("After " + (r + 1) + " left rotation: " + Arrays.toString(arr));
        }
        return arr;
    }

    public static int[] rotateRight(int[] arr, int rotation) {
        /*Lazy rotation. It can be optimized*/

        arr = Arrays.copyOf(arr, arr.length);

        for (int r = 0; r < rotation; r++) {
            int tempValue = arr[arr.length-1];
            for (int i = arr.length-1; i >= 0; i--) {

                if (i == 0) {
                    arr[i] = tempValue;
                    continue;
                }

                arr[i] = arr[i-1];
            }

            System.out.println("After " + (r + 1) + " right rotation: " + Arrays.toString(arr));
        }
        return arr;
    }

    public static void compareArray(int[] arr1, int[] arr2) {
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
    }

    public static void main(String[] args) {
        System.out.println("Array: Rotation of Array");
        int[] arr = ArrayUtils.getRandomArray(10);

        // print whole array
        System.out.println("The current array is: " + Arrays.toString(arr));

        Scanner input = new Scanner(System.in);

        // rotation of array
        System.out.print("How do you want to rotate the array?\n1.l/L:Left rotation\n2.r/R:Right rotation\n=>");
        char rotation = input.next().charAt(0);
        System.out.print("Rotation unit\n=>");
        int rotation_unit = input.nextInt();

        int[] rotatedArray;
        if(Character.toLowerCase(rotation)=='l'){
            rotatedArray = rotateLeft(arr, rotation_unit);
            compareArray(arr, rotatedArray);
        }else if(Character.toLowerCase(rotation)=='r'){
            rotatedArray = rotateRight(arr, rotation_unit);
            compareArray(arr, rotatedArray);
        }else{
            System.out.println("Did not matched any command.\n Skipping statement ...");
        }

    }
}
