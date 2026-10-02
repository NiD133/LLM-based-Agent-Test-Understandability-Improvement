package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig {

    // A long is 8 bytes; reading more than 8 bytes into a long is not supported
    private static final int MAX_BYTES_IN_LONG = 8;

    @Test
    void testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig() {
        // 9 bytes exceeds the 8-byte maximum that fits in a long
        byte[] oversizedArray = new byte[MAX_BYTES_IN_LONG + 1];
        oversizedArray[0] = 1;
        oversizedArray[1] = 2;
        oversizedArray[2] = 3;
        oversizedArray[3] = 4;
        oversizedArray[4] = 5;
        oversizedArray[5] = 6;
        oversizedArray[6] = 7;
        oversizedArray[7] = 8;
        oversizedArray[8] = 9;

        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(oversizedArray));
    }
}
