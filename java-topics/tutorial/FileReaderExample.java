import java.io.*;

public class FileReaderExample {
    public static void main(String[] args) throws Exception{
        FileReader fr = new FileReader("/home/ashish/Documents/dev-resources/java-topics/tutorial/test.txt");
        BufferedReader br = new BufferedReader(fr);

        System.out.println("fr support "+fr.markSupported());
        System.out.println("br support "+br.markSupported());

        System.out.print((char)br.read());
        System.out.print((char)br.read());
        System.out.print((char)br.read());
        br.mark(20);
        System.out.print((char)br.read());
        System.out.print((char)br.read());
        System.out.print((char)br.read());
        System.out.print((char)br.read());

        br.reset();
        System.out.println();
        System.out.print((char)br.read());
        System.out.print((char)br.read());
        System.out.print((char)br.read());
        System.out.print((char)br.read());
        System.out.print((char)br.read());
        System.out.print((char)br.read());

        System.out.println("Whole line \n"+br.readLine());
        System.out.println("Whole line \n"+br.readLine());

        br.close();
        fr.close();
    }
}
