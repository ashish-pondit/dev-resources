import java.io.*;

public class BufferedDemo {

    public static void main(String[] args) throws Exception{
        FileInputStream fis = new FileInputStream("/home/ashish/Documents/dev-resources/java-topics/tutorial/test.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);

        // int x;
        // while ((x=bis.read()) !=-1) {
        //     System.out.print((char)x);
        // }

        System.out.println("FIS support "+fis.markSupported());
        System.out.println("BIS support "+bis.markSupported());

        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        bis.mark(20);
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());

        bis.reset();
        System.out.println();
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());
        System.out.print((char)bis.read());

        bis.close();
        fis.close();
    }
}