package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class Base16Test_testBase16AtBufferEnd {

    private static final String CONTENT = "Hello World";
    private static final String EXPECTED_BASE16_CONTENT = "48656C6C6F20576F726C64";

    private void testBase16InBuffer(final int startPadSize, final int endPadSize) {
        final byte[] contentBytes = StringUtils.getBytesUtf8(CONTENT);

        byte[] paddedBuffer = ArrayUtils.addAll(contentBytes, new byte[endPadSize]);
        paddedBuffer = ArrayUtils.addAll(new byte[startPadSize], paddedBuffer);

        final byte[] encodedBytes = new Base16().encode(paddedBuffer, startPadSize, contentBytes.length);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);

        assertEquals(EXPECTED_BASE16_CONTENT, encodedContent, "encoding hello world");
    }

    @Test
    void testBase16AtBufferEnd() {
        testBase16InBuffer(100, 0);
    }
}
