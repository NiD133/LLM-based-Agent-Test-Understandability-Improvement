package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.InputStreamByteSupplier;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#fromLittleEndian(ByteUtils.ByteSupplier, int)} reads four bytes
 * from a {@link ByteSupplier} and decodes them as a little-endian, unsigned 32-bit value.
 */
public class ByteUtilsTest_testFromLittleEndianFromSupplierUnsignedInt32 {

    @Test
    void testFromLittleEndianFromSupplierUnsignedInt32() throws IOException {
        // Four little-endian bytes: least-significant byte first.
        // The most-significant byte is 128, so the result exceeds Integer.MAX_VALUE
        // and must be treated as an unsigned 32-bit value held in a long.
        final byte[] littleEndianBytes = { 2, 3, 4, (byte) 128 };
        final ByteArrayInputStream source = new ByteArrayInputStream(littleEndianBytes);

        // Reconstruct the expected value from each byte and its little-endian place value.
        final long expected = 2L
                + 3L * 256
                + 4L * 256 * 256
                + 128L * 256 * 256 * 256;

        final long actual = fromLittleEndian(new InputStreamByteSupplier(source), 4);

        assertEquals(expected, actual);
    }
}
