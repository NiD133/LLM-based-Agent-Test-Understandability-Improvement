package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testReadDataFromSetPosition {

    private static final byte[] TEST_DATA = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testReadDataFromSetPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(TEST_DATA)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(4);

            channel.position(5L);
            final int readCount = channel.read(readBuffer);

            assertEquals(4L, readCount);
            assertEquals("data", new String(readBuffer.array(), StandardCharsets.UTF_8));
            assertEquals(TEST_DATA.length, channel.position());
        }
    }
}
