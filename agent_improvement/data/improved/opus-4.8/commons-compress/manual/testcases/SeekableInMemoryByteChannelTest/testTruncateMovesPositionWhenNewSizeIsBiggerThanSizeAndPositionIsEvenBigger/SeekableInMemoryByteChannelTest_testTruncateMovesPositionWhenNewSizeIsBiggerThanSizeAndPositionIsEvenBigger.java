package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link SeekableInMemoryByteChannel#truncate(long)} adjusts the
 * channel position.
 *
 * <p>The {@code SeekableByteChannel} contract states:
 * <q>In either case, if the current position is greater than the given size
 * then it is set to that size.</q></p>
 */
public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenNewSizeIsBiggerThanSizeAndPositionIsEvenBigger {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Scenario: the requested truncation size is larger than the channel's
     * current size, while the current position sits even further beyond that
     * size.
     *
     * <p>Expected outcome:</p>
     * <ul>
     *   <li>The size is left unchanged, because truncate never grows a channel
     *       (the new size is not smaller than the current size).</li>
     *   <li>The position is pulled back to the new size, because it was beyond
     *       it.</li>
     * </ul>
     */
    @Test
    void truncateClampsPositionToNewSizeButLeavesSizeUnchanged() throws Exception {
        final int initialSize = testData.length;
        // New size is one byte beyond the current data: larger than the size,
        // so the size stays the same.
        final long newSize = initialSize + 1;
        // Position is placed well past the new size so truncate must clamp it.
        final long positionBeyondNewSize = 2 * initialSize;

        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(positionBeyondNewSize);

            channel.truncate(newSize);

            assertEquals(initialSize, channel.size(),
                    "size must stay unchanged since the new size is not smaller");
            assertEquals(newSize, channel.position(),
                    "position must be clamped down to the new size");
        }
    }
}
