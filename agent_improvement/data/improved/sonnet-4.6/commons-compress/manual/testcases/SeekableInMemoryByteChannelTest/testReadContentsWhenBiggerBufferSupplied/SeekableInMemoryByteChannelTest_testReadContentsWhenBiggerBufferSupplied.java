package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testReadContentsWhenBiggerBufferSupplied {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * When the supplied read buffer is larger than the available channel data,
     * read() should return only the bytes that are available (not the full buffer
     * capacity), copy those bytes to the start of the buffer, and advance the
     * channel position to the end of the data.
     */
    @Test
    void testReadContentsWhenBiggerBufferSupplied() throws IOException {
        // Allocate a buffer one byte larger than the data so it cannot be filled entirely
        final ByteBuffer oversizedBuffer = ByteBuffer.allocate(testData.length + 1);

        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final int bytesRead = channel.read(oversizedBuffer);

            // Only the available data should have been read, not the full buffer capacity
            assertEquals(testData.length, bytesRead);

            // The bytes placed at the start of the buffer must match the original data
            assertArrayEquals(testData, Arrays.copyOf(oversizedBuffer.array(), testData.length));

            // The channel position must have advanced to the end of the data
            assertEquals(testData.length, channel.position());
        }
    }
}
