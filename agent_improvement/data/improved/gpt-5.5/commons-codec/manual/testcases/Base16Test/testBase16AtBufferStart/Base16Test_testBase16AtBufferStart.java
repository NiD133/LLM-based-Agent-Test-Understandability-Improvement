package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class Base16Test_testBase16AtBufferStart {

    private static final String PLAIN_TEXT = "Hello World";
    private static final String PLAIN_TEXT_BASE16 = "48656C6C6F20576F726C64";

    private void assertBase16EncodingFromBuffer(final int startPadSize, final int endPadSize) {
        final byte[] plainTextBytes = StringUtils.getBytesUtf8(PLAIN_TEXT);

        byte[] paddedBuffer = ArrayUtils.addAll(plainTextBytes, new byte[endPadSize]);
        paddedBuffer = ArrayUtils.addAll(new byte[startPadSize], paddedBuffer);

        final byte[] encodedBytes = new Base16().encode(paddedBuffer, startPadSize, plainTextBytes.length);
        final String encodedText = StringUtils.newStringUtf8(encodedBytes);

        assertEquals(PLAIN_TEXT_BASE16, encodedText, "encoding hello world");
    }

    @Test
    void testBase16AtBufferStart() {
        assertBase16EncodingFromBuffer(0, 100);
    }
}
