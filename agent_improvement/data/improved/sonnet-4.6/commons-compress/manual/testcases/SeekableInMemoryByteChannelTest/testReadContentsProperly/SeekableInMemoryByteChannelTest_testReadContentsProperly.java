package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testReadContentsProperly {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    @DisplayName("read() fills the buffer with the channel's data and advances position to end")
    void testReadContentsProperly() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            ByteBuffer destination = ByteBuffer.allocate(testData.length);

            int bytesRead = channel.read(destination);

            // All bytes must have been transferred in a single read
            assertEquals(testData.length, bytesRead, "bytes read should equal source data length");
            // Buffer must contain an exact copy of the original data
            assertArrayEquals(testData, destination.array(), "buffer contents should match source data");
            // Channel position must have advanced past the last byte
            assertEquals(testData.length, channel.position(), "position should be at end of data after full read");
        }
    }
}
