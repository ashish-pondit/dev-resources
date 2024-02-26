package org.tutorial.chapter10;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class FileCreateAndLastModifiedTime {
    public static void main(String[] args) throws IOException {
        String workingDir = System.getProperty("user.dir");
        String newFile = workingDir+ File.separator+"HelloText.txt";
        Path path = Path.of(newFile);

        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        FileTime creationTime = attr.creationTime();
        System.out.println("Creation time = "+creationTime);

        FileTime lastModifiedTime = attr.lastModifiedTime();
        System.out.println("Last modified time = "+lastModifiedTime);

        long size = attr.size();
        System.out.println("Size = "+size);
    }
}
