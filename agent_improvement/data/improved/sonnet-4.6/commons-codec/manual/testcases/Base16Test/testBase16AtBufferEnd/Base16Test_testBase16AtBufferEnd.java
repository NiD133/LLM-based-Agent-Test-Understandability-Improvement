package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class Base16Test_testBase16AtBufferEnd {

    /**
     * Encodes only the "Hello World" segment of a larger buffer, starting at {@code startPadSize}
     * and spanning {@code bytesUtf8.length} bytes, then asserts the result equals the expected
     * Base16 hex string. This lets callers vary the leading and trailing padding to verify that
     * the encoder respects the offset/length window regardless of surrounding bytes.
     */
    private void testBase16InBuffer(final int startPadSize, final int endPadSize) {
        final String content = "Hello World";
        final byte[] bytesUtf8 = StringUtils.getBytesUtf8(content);

        // Build buffer: [startPadSize zero bytes][content bytes][endPadSize zero bytes]
        byte[] buffer = ArrayUtils.addAll(bytesUtf8, new byte[endPadSize]);
        buffer = ArrayUtils.addAll(new byte[startPadSize], buffer);

        final byte[] encodedBytes = new Base16().encode(buffer, startPadSize, bytesUtf8.length);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);

        assertEquals("48656C6C6F20576F726C64", encodedContent, "encoding hello world");
    }

    /**
     * Verifies that Base16 encoding correctly handles a buffer where the data to encode
     * sits at the very end — preceded by 100 bytes of padding and followed by nothing.
     */
    @Test
    void testBase16AtBufferEnd() {
        testBase16InBuffer(100, 0);
    }
}
