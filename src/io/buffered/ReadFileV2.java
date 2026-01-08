package io.buffered;

import java.io.FileInputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.*;
import static io.buffered.BufferedConst.BUFFER_SIZE;

public class ReadFileV2 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);

        long startTime = System.currentTimeMillis();

        int fileSize = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bufferIndex = 0;
        int size;
        while ((size = fis.read(buffer)) != -1) {
            fileSize += size;
        }

        fis.close();

        long endTime = System.currentTimeMillis();
        System.out.println("File created: " + FILE_NAME);
        System.out.println("File size : " + fileSize / 1024/ 1024 + "MB");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }

}
