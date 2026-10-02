package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hex#encodeHex(byte[], int, int, boolean)} rejects a negative
 * start offset (an offset that is "under" the lower bound of the array).
 */
public class HexTest_testEncodeHexPartialInputUnderbounds {

    @Test
    void testEncodeHexPartialInputUnderbounds() {
        final byte[] data = "hello world".getBytes(StandardCharsets.UTF_8);

        // A start offset of -2 is below index 0, so reading from it must fail.
        final int negativeStartOffset = -2;
        final int length = 10;
        final boolean toLowerCase = true;

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> Hex.encodeHex(data, negativeStartOffset, length, toLowerCase));
    }
}
