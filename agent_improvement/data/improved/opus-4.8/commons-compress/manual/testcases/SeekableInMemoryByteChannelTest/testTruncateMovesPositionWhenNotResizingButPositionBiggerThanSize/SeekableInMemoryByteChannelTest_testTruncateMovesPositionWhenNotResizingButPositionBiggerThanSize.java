package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies the contract of {@link SeekableByteChannel#truncate(long)} for the case where the requested
 * size equals the current size (so no resize happens) but the current position lies beyond it.
 * <p>
 * The Javadoc states: "In either case, if the current position is greater than the given size then it is
 * set to that size." This test exercises exactly that clause.
 * </p>
 */
public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize() throws Exception {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // Move the position past the end of the channel's data.
            final long positionBeyondData = 2L * testData.length;
            channel.position(positionBeyondData);

            // Truncate to the existing size: the size is unchanged, so no resize occurs...
            channel.truncate(testData.length);

            // ...but the position, which was beyond the new size, must be pulled back to that size.
            assertEquals(testData.length, channel.size(), "size should be unchanged");
            assertEquals(testData.length, channel.position(), "position should be clamped to the truncated size");
        }
    }
}
