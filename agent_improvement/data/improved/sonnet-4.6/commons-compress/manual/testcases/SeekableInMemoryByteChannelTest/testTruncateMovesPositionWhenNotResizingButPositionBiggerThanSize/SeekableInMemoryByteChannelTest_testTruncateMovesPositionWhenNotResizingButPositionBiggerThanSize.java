package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies the SeekableByteChannel contract: "if the current position is greater than
 * the given size then it is set to that size" (even when no actual data truncation occurs).
 */
public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize() throws Exception {
        // Arrange: channel holds testData; position is advanced past the end of the data
        long initialDataSize = testData.length;
        long positionBeyondEnd = 2 * initialDataSize;

        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(positionBeyondEnd);

            // Act: truncate to the current data size (no resize), but position > new size
            channel.truncate(initialDataSize);

            // Assert: size is unchanged, and position is clamped down to the new size
            assertEquals(initialDataSize, channel.size(),
                    "Channel size should remain unchanged when truncation target equals current size");
            assertEquals(initialDataSize, channel.position(),
                    "Position should be clamped to the truncation size when position exceeded it");
        }
    }
}
