package org.tutorial.chapter10;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaPathDemo {
    public static void main(String[] args){
        Path path = Path.of("/home/ashish/Documents/java-tutorial/src/main/java/org/tutorial/chapter10/HelloText.text");
        System.out.println(path);

        File file = path.toFile();
        System.out.println("file = "+file);

        File myFile = new File("hello.txt");
        System.out.println("myFile = "+myFile);

        // path seperator
        String workingDir = System.getProperty("user.dir");
        System.out.println("Working directory = "+workingDir);

        String newFile = workingDir+File.separator+"helloworld.txt";
        System.out.println("New file directory = "+newFile);
        File fileHelloWorld = new File(newFile);

        // checking file existence
        System.out.println(newFile + " exists ? = "+ Files.exists(Path.of(newFile)));
        System.out.println(myFile + " exists ? = "+ myFile.exists());

        // check if a path is directory or not
        Path testPath2 = Path.of("hello.txt");
        System.out.println(testPath2 + "is a directory? = "+Files.isDirectory(testPath2));

        testPath2 = Path.of("/home/ashish/Documents");
        System.out.println(testPath2 + "is a directory? = "+Files.isDirectory(testPath2));



    }
}
