package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromStream {

    @Test
    void testFromLittleEndianFromStream() throws IOException {
        // Stream holds four bytes; only the first three are read as a value.
        final ByteArrayInputStream stream = new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 });

        // Little-endian: the first byte is least significant, so the value is
        // 2 * 256^0 + 3 * 256^1 + 4 * 256^2. The trailing byte (5) is ignored.
        final long expected = 2 + 3 * 256 + 4 * 256 * 256;

        assertEquals(expected, fromLittleEndian(stream, 3));
    }
}
