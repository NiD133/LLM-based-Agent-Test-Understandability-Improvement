package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenShrinkingBeyondPosition {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Verifies the {@link SeekableByteChannel#truncate(long)} contract:
     * when the channel is truncated to a size smaller than the current
     * position, the position is pulled back to that new (smaller) size.
     *
     * <p>From the JavaDoc of {@code truncate}: "if the current position is
     * greater than the given size then it is set to that size."</p>
     */
    @Test
    void testTruncateMovesPositionWhenShrinkingBeyondPosition() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // Move the position past the size we are about to truncate to.
            final int positionBeyondNewSize = 4;
            final int newSize = 3;
            channel.position(positionBeyondNewSize);

            channel.truncate(newSize);

            // The channel now reports the smaller size...
            assertEquals(newSize, channel.size(), "size should equal the truncated size");
            // ...and the position has been moved back to that size.
            assertEquals(newSize, channel.position(), "position should be clamped to the truncated size");
        }
    }
}
