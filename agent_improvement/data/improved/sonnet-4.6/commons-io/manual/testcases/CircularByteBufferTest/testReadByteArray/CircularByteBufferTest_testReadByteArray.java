package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testReadByteArray {

    @Test
    @DisplayName("Reading all bytes written to the buffer returns the original content")
    void testReadByteArray() {
        // Arrange: populate the buffer with known ASCII bytes
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final String expectedContent = "0123456789";
        final byte[] bytesIn = expectedContent.getBytes(StandardCharsets.UTF_8);
        buffer.add(bytesIn, 0, bytesIn.length);

        // Act: read the same number of bytes back out
        final byte[] bytesOut = new byte[bytesIn.length];
        buffer.read(bytesOut, 0, bytesIn.length);

        // Assert: the retrieved bytes decode back to the original string
        assertEquals(expectedContent, new String(bytesOut, StandardCharsets.UTF_8));
    }
}
