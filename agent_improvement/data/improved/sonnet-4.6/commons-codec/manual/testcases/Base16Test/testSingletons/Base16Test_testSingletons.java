package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Base16Test_testSingletons {

    private static String encodeByteToHex(final int byteValue) {
        return new String(new Base16().encode(new byte[] { (byte) byteValue }));
    }

    @Test
    void testSingletons() {
        // Each unsigned byte value 0–104 should encode to its 2-digit uppercase hex string
        for (int i = 0; i <= 104; i++) {
            assertEquals(String.format("%02X", i), encodeByteToHex(i));
        }

        // Encode/decode roundtrip must be lossless for every possible byte value
        for (int i = -128; i <= 127; i++) {
            final byte[] test = { (byte) i };
            assertArrayEquals(test, new Base16().decode(new Base16().encode(test)));
        }
    }
}
