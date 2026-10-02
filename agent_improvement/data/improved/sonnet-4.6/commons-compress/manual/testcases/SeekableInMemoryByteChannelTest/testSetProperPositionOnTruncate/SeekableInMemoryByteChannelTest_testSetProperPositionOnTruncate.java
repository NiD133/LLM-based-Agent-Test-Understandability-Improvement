package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testSetProperPositionOnTruncate {

    // Initial channel content: 9 bytes ("Some data")
    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Verifies that truncating a channel to a size smaller than the current position
     * clamps the position down to the new truncation size.
     *
     * Scenario: position is at the end of the data (9), then the channel is truncated
     * to 4 bytes. After truncation, both position and size must equal 4.
     */
    @Test
    void testSetProperPositionOnTruncate() throws IOException {
        final long truncateSize = 4L;

        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // Move position to the end of the initial data so it exceeds truncateSize
            channel.position(testData.length);

            // Truncate the channel — this should also clamp the position to truncateSize
            channel.truncate(truncateSize);

            // Both position and size must equal the new truncated length
            assertEquals(truncateSize, channel.position(),
                    "position should be clamped to the truncation size");
            assertEquals(truncateSize, channel.size(),
                    "size should equal the truncation size");
        }
    }
}
