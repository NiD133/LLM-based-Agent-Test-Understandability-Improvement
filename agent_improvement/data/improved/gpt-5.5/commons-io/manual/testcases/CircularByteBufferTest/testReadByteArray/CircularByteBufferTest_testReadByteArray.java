package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testReadByteArray {

    private static final String CONTENT = "0123456789";
    private static final int CONTENT_LENGTH = 10;

    @Test
    void testReadByteArray() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] bytesIn = CONTENT.getBytes(StandardCharsets.UTF_8);
        final byte[] bytesOut = new byte[CONTENT_LENGTH];

        buffer.add(bytesIn, 0, CONTENT_LENGTH);
        buffer.read(bytesOut, 0, CONTENT_LENGTH);

        assertEquals(CONTENT, new String(bytesOut, StandardCharsets.UTF_8));
    }
}
