package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenNewSizeIsBiggerThanSizeAndPositionIsEvenBigger {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Verifies that when truncate() is called with a new size that is larger than the channel's
     * current data size (so no bytes are removed), but the current position lies beyond that new
     * size, the position is clamped down to the new size.
     *
     * Per the SeekableByteChannel contract:
     * "In either case, if the current position is greater than the given size then it is set to that size."
     *
     * Scenario:
     *   initialSize  = 9  ("Some data")
     *   truncateTo   = 10 (> initialSize  → data unchanged, size stays 9)
     *   startPosition = 18 (> truncateTo  → position clamped to 10)
     */
    @Test
    void testTruncateMovesPositionWhenNewSizeIsBiggerThanSizeAndPositionIsEvenBigger() throws Exception {
        final int initialSize    = testData.length;     // 9
        final long truncateTo    = initialSize + 1;     // 10 — larger than data, so no bytes removed
        final long startPosition = 2 * initialSize;     // 18 — beyond both data and truncateTo

        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(startPosition);
            channel.truncate(truncateTo);

            // truncateTo (10) > initialSize (9), so data is not shrunk
            assertEquals(initialSize, channel.size(),
                    "size should remain unchanged because truncateTo > current size");

            // position (18) > truncateTo (10), so position is clamped to the truncate target
            assertEquals(truncateTo, channel.position(),
                    "position should be clamped to truncateTo when position exceeds it");
        }
    }
}
