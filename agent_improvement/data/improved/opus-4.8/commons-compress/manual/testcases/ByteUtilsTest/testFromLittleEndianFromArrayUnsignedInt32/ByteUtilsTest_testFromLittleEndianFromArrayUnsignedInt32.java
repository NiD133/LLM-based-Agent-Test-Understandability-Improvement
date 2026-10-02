package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayUnsignedInt32 {

    /**
     * Verifies that {@link ByteUtils#fromLittleEndian(byte[], int, int)} reads a
     * 4-byte unsigned little-endian value from a sub-range of the array.
     *
     * <p>Starting at offset 1 and reading 4 bytes, the relevant bytes are
     * {@code 2, 3, 4, 128}. In little-endian order the first byte is the least
     * significant, so the expected value is:</p>
     *
     * <pre>2 + 3*256 + 4*256^2 + 128*256^3</pre>
     *
     * <p>The most significant byte (128) is treated as unsigned, so the result
     * does not fit in a signed int and must be compared as a {@code long}.</p>
     */
    @Test
    void testFromLittleEndianFromArrayUnsignedInt32() {
        // Index:        0  1  2  3   4   -> read 4 bytes starting at offset 1
        final byte[] bytes = { 1, 2, 3, 4, (byte) 128 };
        final int offset = 1;
        final int length = 4;

        final long expected = 2L + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

        assertEquals(expected, fromLittleEndian(bytes, offset, length));
    }
}
