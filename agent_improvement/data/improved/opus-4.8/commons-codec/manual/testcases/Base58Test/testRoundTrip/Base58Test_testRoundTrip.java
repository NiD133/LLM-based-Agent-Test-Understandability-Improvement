package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that encoding a byte array with {@link Base58} and then decoding the
 * result reproduces the original input (a round-trip).
 */
public class Base58Test_testRoundTrip {

    /**
     * A representative selection of inputs: the empty string, short strings of
     * increasing length, a sentence with spaces, digits, and punctuation.
     */
    private static final String[] ROUND_TRIP_INPUTS = {
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
        for (final String input : ROUND_TRIP_INPUTS) {
            final byte[] originalBytes = input.getBytes(StandardCharsets.UTF_8);
            final byte[] encoded = new Base58().encode(originalBytes);
            final byte[] decoded = new Base58().decode(encoded);
            assertArrayEquals(originalBytes, decoded, "Round trip failed for: " + input);
        }
    }
}
