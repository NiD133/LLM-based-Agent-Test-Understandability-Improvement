package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class Base16Test_testBase16AtBufferMiddle {

    private static final String CONTENT = "Hello World";
    private static final String CONTENT_AS_BASE16 = "48656C6C6F20576F726C64";
    private static final int LEADING_PAD_SIZE = 100;
    private static final int TRAILING_PAD_SIZE = 100;

    private void assertBase16EncodesOnlyRequestedBufferSlice(final int startPadSize, final int endPadSize) {
        final byte[] contentBytes = StringUtils.getBytesUtf8(CONTENT);
        byte[] paddedBuffer = ArrayUtils.addAll(contentBytes, new byte[endPadSize]);
        paddedBuffer = ArrayUtils.addAll(new byte[startPadSize], paddedBuffer);

        final byte[] encodedBytes = new Base16().encode(paddedBuffer, startPadSize, contentBytes.length);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);

        assertEquals(CONTENT_AS_BASE16, encodedContent, "encoding hello world");
    }

    @Test
    void testBase16AtBufferMiddle() {
        assertBase16EncodesOnlyRequestedBufferSlice(LEADING_PAD_SIZE, TRAILING_PAD_SIZE);
    }
}
