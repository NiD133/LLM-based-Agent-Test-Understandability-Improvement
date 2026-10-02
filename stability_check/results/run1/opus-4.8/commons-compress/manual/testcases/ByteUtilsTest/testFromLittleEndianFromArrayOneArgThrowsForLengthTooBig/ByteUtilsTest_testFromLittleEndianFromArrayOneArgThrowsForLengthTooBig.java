package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig {

    /**
     * A {@code long} holds at most 8 bytes, so {@link ByteUtils#fromLittleEndian(byte[])}
     * must reject any array longer than 8 bytes. Here we pass a 9-byte array and expect
     * an {@link IllegalArgumentException}.
     */
    @Test
    void testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig() {
        final byte[] tooManyBytesForALong = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(tooManyBytesForALong));
    }
}
