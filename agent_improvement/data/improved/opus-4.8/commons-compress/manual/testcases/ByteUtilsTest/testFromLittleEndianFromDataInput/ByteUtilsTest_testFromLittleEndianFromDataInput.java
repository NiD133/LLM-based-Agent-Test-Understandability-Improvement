package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link ByteUtils#fromLittleEndian(DataInput, int)}, which reads a
 * little-endian number of a given byte length from a {@link DataInput}.
 */
public class ByteUtilsTest_testFromLittleEndianFromDataInput {

    @Test
    void readsThreeLittleEndianBytesFromDataInput() throws IOException {
        // The source holds four bytes, but only the first three are requested below.
        final byte[] sourceBytes = { 2, 3, 4, 5 };
        final DataInput input = new DataInputStream(new ByteArrayInputStream(sourceBytes));

        // In little-endian order each successive byte carries a higher power of 256:
        // byte 0 -> 256^0, byte 1 -> 256^1, byte 2 -> 256^2.
        final long expected = 2 + 3 * 256 + 4 * 256 * 256;

        final long actual = fromLittleEndian(input, 3);

        assertEquals(expected, actual);
    }
}
