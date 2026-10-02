package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInputUnsignedInt32 {

    // Little-endian bytes representing a 32-bit unsigned integer:
    // 0x80040302 = 128*2^24 + 4*2^16 + 3*2^8 + 2
    private static final byte BYTE0 = 2;
    private static final byte BYTE1 = 3;
    private static final byte BYTE2 = 4;
    private static final byte BYTE3 = (byte) 128; // 0x80, high bit set — tests unsigned handling

    // Expected value: bytes assembled as little-endian unsigned 32-bit integer
    private static final long EXPECTED_VALUE =
        2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

    @Test
    void testFromLittleEndianFromDataInputUnsignedInt32() throws IOException {
        DataInput dataInput = new DataInputStream(
            new ByteArrayInputStream(new byte[] { BYTE0, BYTE1, BYTE2, BYTE3 }));

        long result = fromLittleEndian(dataInput, 4);

        assertEquals(EXPECTED_VALUE, result);
    }
}
