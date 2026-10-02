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

    /**
     * Per the SeekableByteChannel contract: "If the given size is greater than or equal
     * to the current size then the entity is not modified." This test verifies that
     * truncating to exactly the current size leaves both the channel size and its
     * content unchanged.
     */
    @Test
    void testTruncateToCurrentSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final long originalSize = testData.length;

            assertEquals(originalSize, channel.size(), "Channel size should match the input data length");

            channel.truncate(originalSize);

            assertEquals(originalSize, channel.size(), "Channel size should be unchanged after truncating to current size");

            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            assertEquals(testData.length, channel.read(readBuffer), "All bytes should be readable after truncation");
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length),
                    "Channel content should be unchanged after truncating to current size");
        }
    }
}
