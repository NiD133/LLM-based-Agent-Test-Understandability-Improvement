package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SeekableInMemoryByteChannel} reads back exactly the bytes
 * it was constructed with and advances its position accordingly.
 */
public class SeekableInMemoryByteChannelTest_testReadContentsProperly {

    /** Backing content the channel is initialized with. */
    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testReadContentsProperly() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {

            // Read the whole channel into a buffer sized to hold all the data.
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            final int bytesRead = channel.read(readBuffer);

            // A single read should consume every byte of the backing data.
            assertEquals(testData.length, bytesRead, "read should return the full number of bytes");
            assertArrayEquals(testData, readBuffer.array(), "buffer should contain the original data");

            // After reading everything, the position should sit at the end of the data.
            assertEquals(testData.length, channel.position(), "position should advance to the end of the data");
        }
    }
}
