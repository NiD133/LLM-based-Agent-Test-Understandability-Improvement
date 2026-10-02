package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hex#encodeHex(byte[], int, int, boolean)} throws
 * {@link ArrayIndexOutOfBoundsException} when the requested range exceeds the
 * bounds of the input array.
 *
 * <p>"hello world" encodes to 11 bytes (indices 0–10). Starting at offset 9
 * and requesting 10 bytes would require reading indices 9–18, which is beyond
 * the array boundary, so an {@link ArrayIndexOutOfBoundsException} must be
 * thrown.</p>
 */
public class HexTest_testEncodeHexPartialInputOverbounds {

    // "hello world" UTF-8 encodes to 11 bytes (indices 0–10)
    private static final String INPUT = "hello world";

    // dataOffset=9, dataLen=10 → would read indices 9..18, exceeding array length 11
    private static final int DATA_OFFSET = 9;
    private static final int DATA_LEN = 10;

    @Test
    void testEncodeHexPartialInputOverbounds() {
        final byte[] data = INPUT.getBytes(StandardCharsets.UTF_8);
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> Hex.encodeHex(data, DATA_OFFSET, DATA_LEN, true));
    }
}
