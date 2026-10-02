package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testKnownDecodings {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * Decodes a lower-case Base16 (hex) string and returns the resulting text.
     */
    private String decodeLowerCaseHex(final String hexEncoded) {
        final boolean lowerCase = true;
        final byte[] decodedBytes = new Base16(lowerCase).decode(hexEncoded.getBytes(CHARSET_UTF8));
        return new String(decodedBytes);
    }

    @Test
    void testKnownDecodings() {
        assertEquals("The quick brown fox jumped over the lazy dogs.",
                decodeLowerCaseHex("54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e"));
        assertEquals("It was the best of times, it was the worst of times.",
                decodeLowerCaseHex("497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e"));
        assertEquals("http://jakarta.apache.org/commmons",
                decodeLowerCaseHex("687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73"));
        assertEquals("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz",
                decodeLowerCaseHex("4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a"));
        assertEquals("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }",
                decodeLowerCaseHex("7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d"));
        assertEquals("xyzzy!",
                decodeLowerCaseHex("78797a7a7921"));
    }
}
