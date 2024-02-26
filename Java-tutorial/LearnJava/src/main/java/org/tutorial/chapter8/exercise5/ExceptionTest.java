package org.tutorial.chapter8.exercise5;

public class ExceptionTest {

    public static void methodA(){
        System.out.println("Inside method A");
    }

    public static void methodB() throws XyzException{
        System.out.println("Inside method B");
        throw new XyzException();
    }

    public static void doNotCallMe() throws Exception{
        throw new Exception("You can't call this method");
    }
    public static void main(String[] args) throws Exception{
        try {
            methodA();
            doNotCallMe();
            methodB();
        }catch (XyzException ex){
            System.out.println("cought xyz exception");
        }catch (Exception e){
            throw e;
        } finally {
            System.out.println("Inside finally block");
        }
    }
}
