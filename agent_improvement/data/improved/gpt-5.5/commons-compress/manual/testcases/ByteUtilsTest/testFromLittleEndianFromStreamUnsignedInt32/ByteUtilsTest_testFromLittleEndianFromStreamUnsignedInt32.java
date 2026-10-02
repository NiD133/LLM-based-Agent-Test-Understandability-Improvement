package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromStreamUnsignedInt32 {

    @Test
    void testFromLittleEndianFromStreamUnsignedInt32() throws IOException {
        final byte leastSignificantByte = 2;
        final byte secondByte = 3;
        final byte thirdByte = 4;
        final byte mostSignificantByte = (byte) 128;
        final ByteArrayInputStream input = new ByteArrayInputStream(
                new byte[] { leastSignificantByte, secondByte, thirdByte, mostSignificantByte });

        final long expectedUnsignedInt32 = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

        assertEquals(expectedUnsignedInt32, fromLittleEndian(input, 4));
    }
}
