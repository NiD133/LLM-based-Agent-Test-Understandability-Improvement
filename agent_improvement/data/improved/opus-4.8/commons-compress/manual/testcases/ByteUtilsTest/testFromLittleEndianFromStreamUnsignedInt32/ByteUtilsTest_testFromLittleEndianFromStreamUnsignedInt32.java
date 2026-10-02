package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#fromLittleEndian(java.io.InputStream, int)} reads four bytes from a
 * stream and assembles them into an unsigned 32-bit value, treating the first byte as the least
 * significant (little-endian order).
 */
public class ByteUtilsTest_testFromLittleEndianFromStreamUnsignedInt32 {

    @Test
    void testFromLittleEndianFromStreamUnsignedInt32() throws IOException {
        // Four little-endian bytes: 0x02 (lowest) ... 0x80 (highest). The 0x80 high byte makes the
        // value exceed Integer.MAX_VALUE, so it must be returned as an unsigned 32-bit number.
        final byte[] littleEndianBytes = { 2, 3, 4, (byte) 128 };
        final ByteArrayInputStream input = new ByteArrayInputStream(littleEndianBytes);

        // Reconstruct the number by weighting each byte with its base-256 position.
        final long expected = 2L + 3L * 256 + 4L * 256 * 256 + 128L * 256 * 256 * 256;

        assertEquals(expected, fromLittleEndian(input, 4));
    }
}
