package charset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;

public class EncodingMain2 {
    private static final Charset EUC_KR = Charset.forName("EUC-KR");
    private static final Charset MS_949 = Charset.forName("MS949");

    public static void main(String[] args) {
        System.out.println("=== ASCII 인코딩 ===");

        test ("A", US_ASCII, US_ASCII);
        test ("ABC", US_ASCII, UTF_16BE);
        test ("ABC", UTF_16BE, EUC_KR);
    }

    /*
     * 한글이 깨지는 가장 큰 2가지 이유
     * 1. EUC-KR, UTF-8이 서로 호환되지 않음
     * 2. EUC-KR,MS949, UTF-8로 인코딩한 한글을 ISO-8859-1 (한글 미지원) 로 디코딩할 때 발생한다.
     */

    private static void test (String text, Charset encodingCharset, Charset decodingCharset) {
        byte[] encoded  = text.getBytes(encodingCharset);
        String decoded = new String(encoded, decodingCharset);
        System.out.printf("%s -> [%s] 인코딩 -> %s %sbyte -> [%s] 디코딩 -> %s\n",
                text, encodingCharset, Arrays.toString(encoded), encoded.length,
                decodingCharset, decoded);
    }
}
