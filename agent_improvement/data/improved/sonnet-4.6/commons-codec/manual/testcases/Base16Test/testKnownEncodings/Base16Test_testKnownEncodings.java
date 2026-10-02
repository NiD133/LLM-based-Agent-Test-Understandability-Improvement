package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class Base16Test_testKnownEncodings {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    @Test
    void testKnownEncodings() {
        final Base16 lowerCaseBase16 = new Base16(true);

        String input;
        String expectedHex;

        input = "The quick brown fox jumped over the lazy dogs.";
        expectedHex = "54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e";
        assertEquals(expectedHex, new String(lowerCaseBase16.encode(input.getBytes(CHARSET_UTF8))));

        input = "It was the best of times, it was the worst of times.";
        expectedHex = "497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e";
        assertEquals(expectedHex, new String(lowerCaseBase16.encode(input.getBytes(CHARSET_UTF8))));

        input = "http://jakarta.apache.org/commmons";
        expectedHex = "687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73";
        assertEquals(expectedHex, new String(lowerCaseBase16.encode(input.getBytes(CHARSET_UTF8))));

        input = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
        expectedHex = "4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a";
        assertEquals(expectedHex, new String(lowerCaseBase16.encode(input.getBytes(CHARSET_UTF8))));

        input = "{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }";
        expectedHex = "7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d";
        assertEquals(expectedHex, new String(lowerCaseBase16.encode(input.getBytes(CHARSET_UTF8))));

        input = "xyzzy!";
        expectedHex = "78797a7a7921";
        assertEquals(expectedHex, new String(lowerCaseBase16.encode(input.getBytes(CHARSET_UTF8))));
    }
}
