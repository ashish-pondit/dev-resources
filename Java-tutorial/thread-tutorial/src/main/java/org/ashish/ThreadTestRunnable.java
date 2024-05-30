package org.ashish;

public class ThreadTestRunnable implements Runnable{
    public void run(){
        int i=1;
        while(true){
            System.out.println(i+" Hello");
            i++;
        }
    }

    public static void main(String[] args) {
        ThreadTestRunnable rn = new ThreadTestRunnable();
        Thread th = new Thread(rn);

        th.start();

        int i=1;
        while(true) {
            System.out.println(i+ " World");
            i++;
        }
    }
}
