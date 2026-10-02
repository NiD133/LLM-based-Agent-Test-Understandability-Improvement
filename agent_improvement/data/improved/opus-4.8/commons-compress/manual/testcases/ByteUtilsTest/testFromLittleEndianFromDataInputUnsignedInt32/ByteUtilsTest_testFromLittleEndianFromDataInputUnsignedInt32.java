package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#fromLittleEndian(DataInput, int)} decodes four
 * bytes read from a {@link DataInput} as an unsigned 32-bit little-endian value.
 */
public class ByteUtilsTest_testFromLittleEndianFromDataInputUnsignedInt32 {

    @Test
    void testFromLittleEndianFromDataInputUnsignedInt32() throws IOException {
        // Little-endian bytes: least significant byte first.
        // The most significant byte (128) has its high bit set, so the result
        // must be treated as unsigned (hence a value larger than Integer.MAX_VALUE).
        final byte[] littleEndianBytes = { 2, 3, 4, (byte) 128 };
        final DataInput input = new DataInputStream(new ByteArrayInputStream(littleEndianBytes));

        // Reconstruct the expected value from each byte and its place value:
        //   byte[0] * 256^0 + byte[1] * 256^1 + byte[2] * 256^2 + byte[3] * 256^3
        final long expectedUnsignedInt32 =
                  2L
                + 3L * 256
                + 4L * 256 * 256
                + 128L * 256 * 256 * 256;

        assertEquals(expectedUnsignedInt32, fromLittleEndian(input, 4));
    }
}
