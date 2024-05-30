package org.ashish;

public class ThreadSecondTest extends Thread {

    public void run(){
        int i=1;
        while(true){
            System.out.println(i+" Hello");
            i++;
        }
    }

    public static void main(String[] args) {
        ThreadSecondTest t = new ThreadSecondTest();
        t.start();

        int i=1;
        while(true) {
            System.out.println(i+ " World");
            i++;
        }
    }
}
