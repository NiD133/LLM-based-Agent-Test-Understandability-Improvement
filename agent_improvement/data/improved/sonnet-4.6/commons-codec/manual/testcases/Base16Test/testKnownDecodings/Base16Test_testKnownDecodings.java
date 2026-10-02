package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testKnownDecodings {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    // Lowercase hex encodings of the expected plaintext strings
    private static final String HEX_QUICK_BROWN_FOX =
            "54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e";
    private static final String HEX_BEST_OF_TIMES =
            "497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e";
    private static final String HEX_URL =
            "687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73";
    private static final String HEX_ALPHABET_MIXED_CASE =
            "4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a";
    private static final String HEX_DIGITS =
            "7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d";
    private static final String HEX_XYZZY =
            "78797a7a7921";

    /**
     * Decodes a lowercase Base16 (hex) string into the corresponding plain-text string.
     */
    private String base16Decode(final String lowerCaseHex) {
        byte[] decoded = new Base16(true).decode(lowerCaseHex.getBytes(CHARSET_UTF8));
        return new String(decoded);
    }

    @Test
    void testKnownDecodings() {
        assertEquals("The quick brown fox jumped over the lazy dogs.",
                base16Decode(HEX_QUICK_BROWN_FOX));
        assertEquals("It was the best of times, it was the worst of times.",
                base16Decode(HEX_BEST_OF_TIMES));
        assertEquals("http://jakarta.apache.org/commmons",
                base16Decode(HEX_URL));
        assertEquals("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz",
                base16Decode(HEX_ALPHABET_MIXED_CASE));
        assertEquals("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }",
                base16Decode(HEX_DIGITS));
        assertEquals("xyzzy!",
                base16Decode(HEX_XYZZY));
    }
}
