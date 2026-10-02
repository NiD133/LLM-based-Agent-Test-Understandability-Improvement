package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CircularByteBuffer#read(byte[], int, int)}.
 */
public class CircularByteBufferTest_testReadByteArray {

    /**
     * Verifies that bytes added to the buffer can be read back in full,
     * unchanged, into a target array using the bulk read method.
     */
    @Test
    void testReadByteArray() {
        // Arrange: add the bytes of a known 10-character string to the buffer.
        final String expectedText = "0123456789";
        final byte[] sourceBytes = expectedText.getBytes(StandardCharsets.UTF_8);
        final int byteCount = sourceBytes.length;

        final CircularByteBuffer buffer = new CircularByteBuffer();
        buffer.add(sourceBytes, 0, byteCount);

        // Act: read all the bytes back into a freshly allocated array.
        final byte[] readBytes = new byte[byteCount];
        buffer.read(readBytes, 0, byteCount);

        // Assert: the bytes read out match the bytes that were added.
        assertEquals(expectedText, new String(readBytes, StandardCharsets.UTF_8));
    }
}
