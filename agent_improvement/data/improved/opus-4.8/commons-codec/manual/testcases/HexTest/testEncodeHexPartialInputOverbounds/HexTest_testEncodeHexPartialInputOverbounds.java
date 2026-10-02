package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHex(byte[], int, int, boolean)} rejects a
 * region that runs past the end of the input array.
 */
public class HexTest_testEncodeHexPartialInputOverbounds {

    @Test
    void encodeHexThrowsWhenRegionExceedsInputLength() {
        // "hello world" is 11 bytes, so indices 0..10 are valid.
        final byte[] data = "hello world".getBytes(StandardCharsets.UTF_8);

        // Starting at offset 9 and reading 10 bytes would reach index 18,
        // well beyond the array, so encoding must fail.
        final int offset = 9;
        final int length = 10;
        final boolean toLowerCase = true;

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> Hex.encodeHex(data, offset, length, toLowerCase));
    }
}
