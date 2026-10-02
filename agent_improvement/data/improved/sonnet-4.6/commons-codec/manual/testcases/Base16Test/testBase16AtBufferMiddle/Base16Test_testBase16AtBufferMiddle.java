package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class Base16Test_testBase16AtBufferMiddle {

    /**
     * Verifies that Base16.encode() correctly encodes only the subrange of a larger buffer,
     * ignoring the leading and trailing padding bytes that surround the content.
     *
     * @param startPadSize number of leading padding bytes before the content
     * @param endPadSize   number of trailing padding bytes after the content
     */
    private void testBase16InBuffer(final int startPadSize, final int endPadSize) {
        final String content = "Hello World";
        final byte[] contentBytes = StringUtils.getBytesUtf8(content);

        // Construct: [startPadSize zero bytes][contentBytes][endPadSize zero bytes]
        byte[] buffer = ArrayUtils.addAll(contentBytes, new byte[endPadSize]);
        buffer = ArrayUtils.addAll(new byte[startPadSize], buffer);

        // Encode only the content slice; the padding bytes must not affect the output
        final byte[] encodedBytes = new Base16().encode(buffer, startPadSize, contentBytes.length);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);

        assertEquals("48656C6C6F20576F726C64", encodedContent,
                "Base16 encoding of 'Hello World' extracted from a padded buffer should match expected hex");
    }

    @Test
    void testBase16AtBufferMiddle() {
        // "Hello World" is placed at offset 100 inside a 211-byte buffer (100 + 11 + 100)
        testBase16InBuffer(100, 100);
    }
}
