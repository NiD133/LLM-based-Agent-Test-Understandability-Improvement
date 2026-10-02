package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig {

    // fromLittleEndian(byte[]) only supports up to 8 bytes (fits in a long)
    private static final int MAX_BYTES_IN_LONG = 8;

    @Test
    void testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig() {
        byte[] nineByteArray = new byte[MAX_BYTES_IN_LONG + 1];
        for (int i = 0; i < nineByteArray.length; i++) {
            nineByteArray[i] = (byte) (i + 1);
        }

        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(nineByteArray));
    }
}
