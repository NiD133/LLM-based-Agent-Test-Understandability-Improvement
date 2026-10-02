package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testKnownEncodings {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * Encodes the given text to lower-case Base16 (hexadecimal) using UTF-8 bytes.
     */
    private static String encodeToLowerCaseHex(final String text) {
        final byte[] encoded = new Base16(true).encode(text.getBytes(CHARSET_UTF8));
        return new String(encoded);
    }

    @Test
    void testKnownEncodings() {
        assertEquals(
                "54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e",
                encodeToLowerCaseHex("The quick brown fox jumped over the lazy dogs."));
        assertEquals(
                "497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e",
                encodeToLowerCaseHex("It was the best of times, it was the worst of times."));
        assertEquals(
                "687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73",
                encodeToLowerCaseHex("http://jakarta.apache.org/commmons"));
        assertEquals(
                "4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a",
                encodeToLowerCaseHex("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz"));
        assertEquals(
                "7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d",
                encodeToLowerCaseHex("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }"));
        assertEquals(
                "78797a7a7921",
                encodeToLowerCaseHex("xyzzy!"));
    }
}
