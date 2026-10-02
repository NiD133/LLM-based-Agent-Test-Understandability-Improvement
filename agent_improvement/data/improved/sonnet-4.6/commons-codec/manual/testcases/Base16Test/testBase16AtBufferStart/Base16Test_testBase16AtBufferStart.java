package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class Base16Test_testBase16AtBufferStart {

    /**
     * Builds a buffer of the form: [startPadSize zero bytes] + [UTF-8 content bytes] + [endPadSize zero bytes],
     * then encodes only the content portion using Base16 and asserts the result matches the expected hex string.
     * This verifies that Base16.encode correctly handles an offset and length within a larger byte array.
     */
    private void testBase16InBuffer(final int startPadSize, final int endPadSize) {
        final String content = "Hello World";
        final byte[] contentBytes = StringUtils.getBytesUtf8(content);

        // Construct buffer with leading and trailing padding around the content
        byte[] buffer = ArrayUtils.addAll(contentBytes, new byte[endPadSize]);
        buffer = ArrayUtils.addAll(new byte[startPadSize], buffer);

        final byte[] encodedBytes = new Base16().encode(buffer, startPadSize, contentBytes.length);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);

        assertEquals("48656C6C6F20576F726C64", encodedContent, "encoding hello world");
    }

    /**
     * Tests that Base16 encoding works correctly when the data occupies the very start of the buffer
     * (no leading padding), with trailing unused bytes following the content.
     */
    @Test
    void testBase16AtBufferStart() {
        testBase16InBuffer(0, 100);
    }
}
