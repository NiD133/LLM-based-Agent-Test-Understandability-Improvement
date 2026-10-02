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
     * When the supplied buffer is larger than the channel's contents, the read should copy only
     * the available bytes (not fill the whole buffer), report that count, and advance the position
     * to the end of the data.
     */
    @Test
    void testReadContentsWhenBiggerBufferSupplied() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // Allocate a buffer with one extra byte of capacity beyond the data length.
            final ByteBuffer oversizedBuffer = ByteBuffer.allocate(testData.length + 1);

            final int bytesRead = channel.read(oversizedBuffer);

            // Only the available data is read, leaving the surplus buffer capacity untouched.
            assertEquals(testData.length, bytesRead, "should read exactly the number of bytes available");
            final byte[] dataPortion = Arrays.copyOf(oversizedBuffer.array(), testData.length);
            assertArrayEquals(testData, dataPortion, "read bytes should match the original data");
            assertEquals(testData.length, channel.position(), "position should advance to the end of the data");
        }
    }
}
