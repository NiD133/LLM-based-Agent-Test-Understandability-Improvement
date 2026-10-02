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

    @Test
    void testReadContentsWhenBiggerBufferSupplied() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length + 1);

            final int bytesRead = channel.read(readBuffer);
            final byte[] bytesCopiedIntoBuffer = Arrays.copyOf(readBuffer.array(), testData.length);

            assertEquals(testData.length, bytesRead);
            assertArrayEquals(testData, bytesCopiedIntoBuffer);
            assertEquals(testData.length, channel.position());
        }
    }
}
