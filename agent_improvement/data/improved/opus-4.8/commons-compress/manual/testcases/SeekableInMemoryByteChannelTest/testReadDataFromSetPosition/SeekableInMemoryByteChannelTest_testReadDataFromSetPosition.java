package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testReadDataFromSetPosition {

    /** Channel contents; characters 5..8 ("data") are the slice we expect to read back. */
    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testReadDataFromSetPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // Skip over "Some " so reading starts at the 'd' in "data".
            final long startPosition = 5L;
            channel.position(startPosition);

            // Read the remaining 4 bytes ("data") into a buffer sized to hold exactly that.
            final ByteBuffer readBuffer = ByteBuffer.allocate(4);
            final int bytesRead = channel.read(readBuffer);

            assertEquals(4L, bytesRead, "should read all 4 remaining bytes");
            assertEquals("data", new String(readBuffer.array(), StandardCharsets.UTF_8),
                "buffer should contain the bytes starting at the set position");
            assertEquals(testData.length, channel.position(),
                "position should advance to the end of the data after the read");
        }
    }
}
