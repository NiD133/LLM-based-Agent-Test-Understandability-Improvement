package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testReadContentsProperly {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testReadContentsProperly() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer destination = ByteBuffer.allocate(testData.length);

            final int bytesRead = channel.read(destination);

            assertEquals(testData.length, bytesRead);
            assertArrayEquals(testData, destination.array());
            assertEquals(testData.length, channel.position());
        }
    }
}
