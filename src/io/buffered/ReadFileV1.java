package io.buffered;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.buffered.BufferedConst.*;

public class ReadFileV1 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);

        long startTime = System.currentTimeMillis();

        int fileSize = 0;
        int data;
        while ((data = fis.read()) != -1) {
            fileSize++;
        }
        fis.close();

        long endTime = System.currentTimeMillis();
        System.out.println("File created: " + FILE_NAME);
        System.out.println("File size : " + fileSize / 1024/ 1024 + "MB");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }

    /*
     * 오래 걸린 이유는 자바에서 1byte씩 디스크에 데이터를 전달하기 때문. 이 과정을 1000만번 반복함
     * 더 자세히 설명하면
     * 1. write, read를 호출할 때 마다 OS의 시스템 콜을 통해 파일을 읽거나 쓰는 명령어를 전달한다. 이러한 시스템 콜은 상대적으로 무거운 작업
     * 2. HDD, SDD같은 장치들도 하나의 데이터를 일고 쓸 때마다 필요한 시간이 있다. HDD의 경우 더욱 느린데 물리적으로 디스크의 회전이 필요
     * 3. 이러한 무거운 작업을 무려 1000만번 반복
     * 자바에서 운영 체제를 통해 디스크에 1byte씩 전달하면, os혹은 하드웨어 레벨에서 여러가지 최적화가 발생하므로 1byte를 계속 쓰는건아니다.
     * write, read를 호출할 때마다 발생하는 시스템 콜이 주원인.
     */
}
