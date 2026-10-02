package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenShrinkingBeyondPosition {

    private static final int POSITION_BEYOND_TRUNCATED_SIZE = 4;
    private static final int TRUNCATED_SIZE = 3;

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /*
     * If the current position is greater than the given size, truncate moves
     * the current position back to that size.
     */
    @Test
    void testTruncateMovesPositionWhenShrinkingBeyondPosition() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(POSITION_BEYOND_TRUNCATED_SIZE);
            channel.truncate(TRUNCATED_SIZE);

            assertEquals(TRUNCATED_SIZE, channel.size());
            assertEquals(TRUNCATED_SIZE, channel.position());
        }
    }
}
