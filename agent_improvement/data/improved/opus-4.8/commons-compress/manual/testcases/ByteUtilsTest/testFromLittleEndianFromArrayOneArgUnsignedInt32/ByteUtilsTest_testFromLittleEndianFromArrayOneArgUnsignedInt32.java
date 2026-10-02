package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgUnsignedInt32 {

    /**
     * Verifies that {@link ByteUtils#fromLittleEndian(byte[])} decodes a 4-byte
     * little-endian array into the correct unsigned 32-bit value.
     *
     * <p>In little-endian order the least significant byte comes first, so the
     * bytes {@code {2, 3, 4, 0x80}} represent:
     * {@code 2 + 3*256 + 4*256^2 + 128*256^3}. The top byte is {@code 0x80},
     * which keeps the value above {@link Integer#MAX_VALUE}; the {@code 128L}
     * factor forces 64-bit arithmetic so the result stays unsigned in the long.</p>
     */
    @Test
    void readsFourBytesAsUnsignedInt32() {
        final byte[] littleEndianBytes = { 2, 3, 4, (byte) 128 };

        final long expected = 2L + 3L * 256 + 4L * 256 * 256 + 128L * 256 * 256 * 256;

        assertEquals(expected, fromLittleEndian(littleEndianBytes));
    }
}
