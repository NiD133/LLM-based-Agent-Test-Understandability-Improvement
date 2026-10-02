package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Base16} encodes and decodes single-byte inputs correctly.
 */
public class Base16Test_testSingletons {

    /** Encodes one byte and returns the resulting Base16 text. */
    private static String encodeByte(final int value) {
        return new String(new Base16().encode(new byte[] { (byte) value }));
    }

    @Test
    void testSingletons() {
        // Each byte value from 0 to 104 must encode to its two-digit, upper-case hex string,
        // e.g. 0 -> "00", 10 -> "0A", 104 -> "68".
        for (int value = 0; value <= 104; value++) {
            final String expectedHex = String.format("%02X", value);
            assertEquals(expectedHex, encodeByte(value), "encoding byte " + value);
        }

        // Encoding then decoding any byte must round-trip back to the original byte.
        for (int value = -128; value <= 127; value++) {
            final byte[] original = { (byte) value };
            final byte[] roundTripped = new Base16().decode(new Base16().encode(original));
            assertArrayEquals(original, roundTripped, "round-trip of byte " + value);
        }
    }
}
