import java.io.File;
import java.io.FileOutputStream;

public class FileHandle {

    public static void main(String ...args) throws Exception{
        File f = new File(".");
        String list[] = f.list();

        for(String x:list) {
            System.out.println(x);
        }

        
        File listFile[] = f.listFiles();

        for(File x:listFile) {
            System.out.println(x.getName() + "  --- " +x.getPath());
        }


        File f2 = new File("java-topics/tutorial/file_handle.txt");
        // f2.setReadOnly();
        // f2.setWritable(true);

        System.out.println("Las modified: " + f2.lastModified());

        FileOutputStream fos = new FileOutputStream("java-topics/tutorial/file_handle.txt");
        fos.close();

    }
    
}
