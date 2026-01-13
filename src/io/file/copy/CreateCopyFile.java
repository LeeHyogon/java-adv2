package io.file.copy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class CreateCopyFile {
    private static final int FILE_SIZE = 200 * 1024 * 1024;

    public static void main(String[] args) throws IOException {
        String fileName = "temp/copy.dat";
        long startTime = System.currentTimeMillis();
        FileOutputStream fos = new FileOutputStream(fileName);
        byte[] buffer = new byte[FILE_SIZE];
        fos.write(buffer);
        fos.close();

        long endTime = System.currentTimeMillis();
        System.out.println("fileName = " + fileName);
        System.out.println("FILE_SIZE/1024/1024 + \"MB\" = " + FILE_SIZE / 1024 / 1024 + "MB");
        System.out.println("endTime -startTime + \"ms\" = " + (endTime - startTime) + "ms");

    }
}
