package io.file.text;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ReadTextFileV2 {
    private static final String PATH = "temp/hello2.txt";
    public static void main(String[] args) throws IOException {


        String writeString = "abc\n가나다";

        Path path = Path.of(PATH);

        //파일에 쓰기
        Files.writeString(path, writeString, UTF_8);
        //파일에서 읽기

        System.out.println("==readString === ");
        //파일을 한 번에 다 읽고, 라인 단위로 나누어 저장하고 반환하므로 memory 문제 발생
        List<String> lines = Files.readAllLines(path, UTF_8);

        for (String line : lines) {
            System.out.println("line = " + line);
        }

        Stream<String> lineStream = Files.lines(path, UTF_8);
        lineStream.forEach(line -> System.out.println("line = " + line));
        lineStream.close();
    }
}
