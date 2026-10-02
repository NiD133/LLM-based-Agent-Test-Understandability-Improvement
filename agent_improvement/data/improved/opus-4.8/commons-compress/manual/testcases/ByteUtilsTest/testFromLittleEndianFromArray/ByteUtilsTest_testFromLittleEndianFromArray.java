package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ByteUtils#fromLittleEndian(byte[], int, int)}, which reads a
 * slice of a byte array as a little-endian (least-significant byte first) long.
 */
public class ByteUtilsTest_testFromLittleEndianFromArray {

    @Test
    void testFromLittleEndianFromArray() {
        final byte[] bytes = { 1, 2, 3, 4, 5 };

        // Read 3 bytes starting at index 1, i.e. the slice { 2, 3, 4 }.
        // Little-endian means the first byte is the lowest-order one:
        //   2 * 256^0 + 3 * 256^1 + 4 * 256^2.
        final int offset = 1;
        final int length = 3;
        final long expected = 2 + 3 * 256 + 4 * 256 * 256;

        assertEquals(expected, fromLittleEndian(bytes, offset, length));
    }
}
