package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base58Test_testRoundTrip {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static final String[] ROUND_TRIP_STRINGS = {
            "",
            "a",
            "ab",
            "abc",
            "abcd",
            "abcde",
            "abcdef",
            "Hello World",
            "The quick brown fox jumps over the lazy dog",
            "1234567890",
            "!@#$%^&*()"
    };

    @Test
    void testRoundTrip() {
        for (final String test : ROUND_TRIP_STRINGS) {
            assertRoundTripsThroughBase58(test);
        }
    }

    private static void assertRoundTripsThroughBase58(final String test) {
        final byte[] input = test.getBytes(CHARSET_UTF8);
        final byte[] encoded = new Base58().encode(input);
        final byte[] decoded = new Base58().decode(encoded);

        assertArrayEquals(input, decoded, "Round trip failed for: " + test);
    }
}
