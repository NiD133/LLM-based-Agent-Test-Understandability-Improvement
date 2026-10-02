package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromStreamUnsignedInt32 {

    @Test
    void testFromLittleEndianFromStreamUnsignedInt32() throws IOException {
        // Little-endian bytes [0x02, 0x03, 0x04, 0x80] represent the 32-bit unsigned value 0x80040302
        byte[] inputBytes = { 2, 3, 4, (byte) 128 };
        long expectedValue = 0x80040302L;

        ByteArrayInputStream bin = new ByteArrayInputStream(inputBytes);
        assertEquals(expectedValue, fromLittleEndian(bin, 4));
    }
}
