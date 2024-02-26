package org.tutorial.chapter10;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.stream.Stream;

public class FileCreation {

    public static void firstMethodToCreate() throws IOException {
        Path filePath = Path.of("Hello.txt");
        if(!Files.exists(filePath))
        {
            Path file = Files.createFile(filePath);
            System.out.println("File = "+file);
            System.out.println(filePath+" exists? = "+Files.exists(file));
        }
    }

    public static void secondMethodToCreate() throws IOException{
        File myFile = new File("secondFile.txt");
        boolean hasFileCreated = myFile.createNewFile();

        System.out.println("File created ? "+hasFileCreated);
    }

    public static void createDirectory(String path) throws IOException{
        Path myFolder = Path.of(path);
        if(!Files.exists(myFolder)){
            Path directory = Files.createDirectory(myFolder);
        }
    }

    public static void createDirectories() throws IOException{
        Path myFolder = Path.of("ParentFolder");
        Path createdPath = Files.createDirectories(myFolder);
        System.out.println(createdPath);
    }

    public static void showDirectoryPermission(String directoryPath) throws IOException {
        Path path = Path.of(directoryPath);
        Set<PosixFilePermission> posixFilePermissions = Files.getPosixFilePermissions(path);
        System.out.println(directoryPath + " " +posixFilePermissions);
    }

    public static void createDirectoryWithPermission(String directoryPath, String permission) throws IOException{
        if(permission.isEmpty()){
            createDirectory(directoryPath);
            return;
        }

        Path path = Path.of(directoryPath);
        if(!Files.exists(path)){
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString(permission);
            FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);

            Files.createDirectory(path, fileAttributes);
        }
    }


    public static void moveOrRenameFile(String oldFilePath, String newFilePath) throws IOException {
        Path oldPath = Path.of(oldFilePath);
        Path newPath = Path.of((newFilePath));

        Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void deleteFile(String path) throws IOException {
        Path filePath = Path.of(path);
        Files.delete(filePath);
    }

    public static void copyFile(String originalFile, String copyFile) throws IOException {
        Path copyFromPath = Path.of(originalFile);
        Path copyTo = Path.of(copyFile);

        Files.copy(copyFromPath, copyTo, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void listNested(String directory) throws IOException {
        Path directoryPath = Path.of(directory);
        System.out.println("List of things in the directory");
        Stream<Path> walk = Files.walk(directoryPath);
        walk.forEach(file -> {
            System.out.println(file);
        });
    }

    public static void listFile(String directory) throws IOException {
        Path directoryPath = Path.of(directory);

        System.out.println("List of things in the directory: ");

        Stream<Path> list = Files.list(directoryPath);
        list.forEach(file -> {
            System.out.println(file);
        });
    }

    public static void main(String[] args) throws IOException {
//        firstMethodToCreate();
//        secondMethodToCreate();
//
//        createDirectory("TestFolder");
//        createDirectories();

//        showDirectoryPermission("ParentFolder");

//        createDirectoryWithPermission("SecondParent", "rwxr--r--");
//        showDirectoryPermission("SecondParent");

//        moveOrRenameFile("Hello.txt", "HelloRename.txt");
//        moveOrRenameFile("HelloRename.txt", "ParentFolder/HelloRename.txt");

//        copyFile("HelloText.txt", "HelloTextCopy.txt");
//        deleteFile("HelloTextCopy.txt");
//        System.out.println(System.getProperty("user.dir"));
//        listFile(System.getProperty("user.dir"));
            listNested(System.getProperty("user.dir"));

    }
}
