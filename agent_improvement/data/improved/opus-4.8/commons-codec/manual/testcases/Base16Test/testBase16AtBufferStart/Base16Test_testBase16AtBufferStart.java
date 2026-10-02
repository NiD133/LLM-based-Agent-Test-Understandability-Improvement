package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16#encode(byte[], int, int)} correctly encodes a
 * region of a larger byte buffer, honouring the supplied offset and length.
 */
public class Base16Test_testBase16AtBufferStart {

    /** Plain-text payload that is encoded in every scenario below. */
    private static final String CONTENT = "Hello World";

    /** Expected upper-case Base16 (hex) encoding of {@link #CONTENT}. */
    private static final String EXPECTED_ENCODING = "48656C6C6F20576F726C64";

    /**
     * Encodes {@link #CONTENT} after embedding it inside a larger buffer that is
     * padded with zero bytes before and after the payload, then asserts that only
     * the payload region is encoded.
     *
     * @param startPadSize number of zero bytes placed before the payload.
     * @param endPadSize   number of zero bytes placed after the payload.
     */
    private void assertPayloadEncodedWithinPaddedBuffer(final int startPadSize, final int endPadSize) {
        final byte[] payloadBytes = StringUtils.getBytesUtf8(CONTENT);

        // Build: [startPad zeros][payload][endPad zeros]
        byte[] buffer = ArrayUtils.addAll(payloadBytes, new byte[endPadSize]);
        buffer = ArrayUtils.addAll(new byte[startPadSize], buffer);

        // Encode only the payload region (skip the leading padding).
        final byte[] encodedBytes = new Base16().encode(buffer, startPadSize, payloadBytes.length);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);

        assertEquals(EXPECTED_ENCODING, encodedContent, "encoding hello world");
    }

    @Test
    void testBase16AtBufferStart() {
        // Payload sits at the very start of the buffer (no leading padding),
        // followed by 100 bytes of trailing padding.
        assertPayloadEncodedWithinPaddedBuffer(0, 100);
    }
}
