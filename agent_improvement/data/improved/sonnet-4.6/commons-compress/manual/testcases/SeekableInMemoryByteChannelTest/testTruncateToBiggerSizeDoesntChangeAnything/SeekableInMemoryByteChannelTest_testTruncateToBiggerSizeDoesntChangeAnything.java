package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateToBiggerSizeDoesntChangeAnything {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Per the SeekableByteChannel spec: "If the given size is greater than or equal
     * to the current size then the entity is not modified."
     *
     * Verifies that calling truncate() with a size larger than the channel's current
     * size leaves both the reported size and the readable content unchanged.
     */
    @Test
    void testTruncateToBiggerSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            long originalSize = testData.length;
            long oversizedTruncateTarget = originalSize + 1;

            // Confirm baseline: channel size equals the number of bytes written
            assertEquals(originalSize, channel.size());

            // Truncating to a size larger than current size must be a no-op
            channel.truncate(oversizedTruncateTarget);
            assertEquals(originalSize, channel.size(), "size must not change when truncate target exceeds current size");

            // Position was not advanced, so all original bytes must still be readable
            ByteBuffer readBuffer = ByteBuffer.allocate((int) originalSize);
            int bytesRead = channel.read(readBuffer);
            assertEquals(originalSize, bytesRead, "all original bytes must remain readable");
            byte[] actualContent = Arrays.copyOf(readBuffer.array(), (int) originalSize);
            assertArrayEquals(testData, actualContent, "content must be identical to original data");
        }
    }
}
