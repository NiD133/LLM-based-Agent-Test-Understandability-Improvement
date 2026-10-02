package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#toLittleEndian(DataOutput, long, int)} writes a
 * numeric value to a {@link DataOutput} as a little-endian byte sequence.
 */
public class ByteUtilsTest_testToLittleEndianToDataOutput {

    @Test
    void testToLittleEndianToDataOutput() throws IOException {
        // In little-endian order the least significant byte comes first, so the
        // value below is built from the bytes 2, 3 and 4 (low to high).
        final long value = 2 + 3 * 256 + 4 * 256 * 256;
        final int lengthInBytes = 3;
        final byte[] expectedLittleEndianBytes = { 2, 3, 4 };

        final byte[] writtenBytes;
        try (ByteArrayOutputStream backingStream = new ByteArrayOutputStream()) {
            final DataOutput dataOutput = new DataOutputStream(backingStream);

            toLittleEndian(dataOutput, value, lengthInBytes);

            writtenBytes = backingStream.toByteArray();
            assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
        }

        // The bytes captured before closing the stream remain unchanged afterwards.
        assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
    }
}
