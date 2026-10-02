package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Verifies that truncating a {@link SeekableInMemoryByteChannel} to its current
 * size is a no-op, as required by the {@link SeekableByteChannel#truncate(long)}
 * contract:
 *
 * <blockquote>If the given size is greater than or equal to the current size
 * then the entity is not modified.</blockquote>
 */
public class SeekableInMemoryByteChannelTest_testTruncateToCurrentSizeDoesntChangeAnything {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testTruncateToCurrentSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // The channel starts out holding exactly the test data.
            assertEquals(testData.length, channel.size(), "initial size should match the test data");

            // Truncating to the current size must leave the channel unchanged.
            channel.truncate(testData.length);
            assertEquals(testData.length, channel.size(), "size should be unchanged after truncating to current size");

            // All of the original bytes should still be readable and identical.
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            assertEquals(testData.length, channel.read(readBuffer), "should read back every byte of the test data");
            final byte[] bytesRead = Arrays.copyOf(readBuffer.array(), testData.length);
            assertArrayEquals(testData, bytesRead, "read content should be identical to the test data");
        }
    }
}
