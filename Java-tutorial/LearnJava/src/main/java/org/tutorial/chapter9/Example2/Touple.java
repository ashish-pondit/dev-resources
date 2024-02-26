package org.tutorial.chapter9.Example2;

public class Touple<X, Y> {
    private final X x;
    private final Y y;
    public Touple(X x, Y y){
        this.x = x;
        this.y = y;
    }

    public X getX(){
        return this.x;
    }

    public Y getY(){
        return this.y;
    }

    public void showTypes(){
        System.out.println("Type of X is "+ x.getClass().getName()+" and Value: "+x);
        System.out.println("Type of Y is "+ y.getClass().getName()+" and Value: "+y);
    }
}
