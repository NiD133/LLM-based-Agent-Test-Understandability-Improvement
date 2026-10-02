package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayUnsignedInt32 {

    @Test
    void testFromLittleEndianFromArrayUnsignedInt32() {
        final byte[] bytes = { 1, 2, 3, 4, (byte) 128 };
        final int unsignedInt32Offset = 1;
        final int unsignedInt32Length = 4;
        final long expectedUnsignedInt32 = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

        assertEquals(expectedUnsignedInt32, fromLittleEndian(bytes, unsignedInt32Offset, unsignedInt32Length));
    }
}
