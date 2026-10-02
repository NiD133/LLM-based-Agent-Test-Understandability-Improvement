package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testSignalEOFWhenPositionAtTheEnd {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testSignalEOFWhenPositionAtTheEnd() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);

            channel.position(testData.length + 1);
            final int readCount = channel.read(readBuffer);

            assertEquals(0L, readBuffer.position());
            assertEquals(-1, readCount);
            assertEquals(-1, channel.read(readBuffer));
        }
    }
}
