package charset;

import java.nio.charset.Charset;
import java.util.SortedMap;

public class AvailableCharsetsMain {
    public static void main(String[] args) {
        // 이용 가능한 모든 Charset 자바 + OS
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (String charsetName : charsets.keySet()) {

            System.out.println("charsetNmae = " + charsetName);
        }

        System.out.println("=====");
        // 문자로 조회 (대소문자 구분 X),
        Charset charset1 = Charset.forName("MS949");
        System.out.println("charset1 = " + charset1);

        Charset defaultCharset = Charset.defaultCharset();
        System.out.println("defaultCharset = " + defaultCharset);
    }
}
