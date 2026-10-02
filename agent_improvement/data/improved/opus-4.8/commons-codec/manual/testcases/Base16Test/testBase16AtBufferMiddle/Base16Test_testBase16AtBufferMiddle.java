package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16#encode(byte[], int, int)} encodes only the requested
 * region of a byte array, ignoring any surrounding padding bytes.
 */
public class Base16Test_testBase16AtBufferMiddle {

    /** Plain-text payload to encode, and its expected Base16 (hex) representation. */
    private static final String PLAIN_TEXT = "Hello World";
    private static final String EXPECTED_HEX = "48656C6C6F20576F726C64";

    /**
     * Places the encoded payload inside a larger buffer that is padded with zero
     * bytes before and after the payload, then asserts that only the payload region
     * is encoded.
     *
     * @param leadingPadSize  number of zero bytes placed before the payload.
     * @param trailingPadSize number of zero bytes placed after the payload.
     */
    private void assertEncodesPayloadRegion(final int leadingPadSize, final int trailingPadSize) {
        final byte[] payloadBytes = StringUtils.getBytesUtf8(PLAIN_TEXT);

        // Build: [leadingPad][payload][trailingPad]
        byte[] buffer = ArrayUtils.addAll(payloadBytes, new byte[trailingPadSize]);
        buffer = ArrayUtils.addAll(new byte[leadingPadSize], buffer);

        // Encode only the payload region, which starts right after the leading padding.
        final byte[] encodedBytes = new Base16().encode(buffer, leadingPadSize, payloadBytes.length);
        final String encodedHex = StringUtils.newStringUtf8(encodedBytes);

        assertEquals(EXPECTED_HEX, encodedHex, "encoding hello world");
    }

    @Test
    void testBase16AtBufferMiddle() {
        assertEncodesPayloadRegion(100, 100);
    }
}
