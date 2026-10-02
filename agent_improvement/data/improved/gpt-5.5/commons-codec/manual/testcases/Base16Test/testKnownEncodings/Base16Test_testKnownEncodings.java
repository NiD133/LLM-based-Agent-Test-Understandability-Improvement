package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testKnownEncodings {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private void assertLowerCaseEncoding(final String plainText, final String expectedEncodedText) {
        assertEquals(expectedEncodedText, new String(new Base16(true).encode(plainText.getBytes(CHARSET_UTF8))));
    }

    @Test
    void testKnownEncodings() {
        assertLowerCaseEncoding(
                "The quick brown fox jumped over the lazy dogs.",
                "54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e");
        assertLowerCaseEncoding(
                "It was the best of times, it was the worst of times.",
                "497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e");
        assertLowerCaseEncoding(
                "http://jakarta.apache.org/commmons",
                "687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73");
        assertLowerCaseEncoding(
                "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz",
                "4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a");
        assertLowerCaseEncoding(
                "{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }",
                "7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d");
        assertLowerCaseEncoding("xyzzy!", "78797a7a7921");
    }
}
