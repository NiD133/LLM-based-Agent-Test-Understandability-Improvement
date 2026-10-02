package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInput {

    /**
     * Verifies that fromLittleEndian(DataInput, length) reads {@code length} bytes
     * and assembles them as a little-endian unsigned long.
     *
     * Input stream:  [0x02, 0x03, 0x04, 0x05]
     * Read 3 bytes:   byte[0]=2 | byte[1]=3<<8 | byte[2]=4<<16  =>  0x040302
     */
    @Test
    void testFromLittleEndianFromDataInput() throws IOException {
        // Four bytes in the stream; only the first three are consumed by the call.
        DataInput din = new DataInputStream(new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 }));

        // Expected value assembled manually: 2 + 3*256 + 4*256*256 = 0x040302
        long expected = 2 + 3 * 256 + 4 * 256 * 256;

        assertEquals(expected, fromLittleEndian(din, 3));
    }
}
