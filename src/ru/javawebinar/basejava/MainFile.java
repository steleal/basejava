package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("src/ru/javawebinar");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("======================================================");
        printDirectoryDeeply(dir, 0);

    }

    public static void printDirectoryDeeply(File dir, int deep) {
        File[] files = dir.listFiles();
        if (files == null) return;
        Arrays.sort(files, Comparator.comparing(File::isDirectory).reversed());
        String tabs = deep == 0 ? "" : String.format("%" + 10 * deep + "s", " ");
        for (File file : files) {
            System.out.print( tabs);
            String name = file.getName();
            if (file.isFile()) {
                System.out.println("File: " + name);
            } else if (file.isDirectory()) {
                System.out.println("Dir:  " + name);
                printDirectoryDeeply(file, deep + 1);
            }
        }
    }

}
