package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateToCurrentSizeDoesntChangeAnything {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /*
     * <q>If the given size is greater than or equal to the current size then the entity is not modified.</q>
     */
    @Test
    void testTruncateToCurrentSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            assertEquals(testData.length, channel.size());

            channel.truncate(testData.length);

            assertEquals(testData.length, channel.size());
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            assertEquals(testData.length, channel.read(readBuffer));
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }
}
