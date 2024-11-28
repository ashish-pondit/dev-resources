import java.io.*;

public class RandomAccessDemo {
    public static void main(String ...args) throws Exception{
        System.out.println("hello file access");

        RandomAccessFile rf = new RandomAccessFile("java-topics/tutorial/letters.txt", "rw");
        System.out.println((char)rf.read());
        System.out.println((char)rf.read());
        System.out.println((char)rf.read());

        rf.write('d');

        System.out.println((char)rf.read());
        rf.skipBytes(3);
        System.out.println((char)rf.read());

        rf.seek(3);
        System.out.println((char)rf.read());

        System.out.println(rf.getFilePointer());

        rf.close();
    }
    
}
