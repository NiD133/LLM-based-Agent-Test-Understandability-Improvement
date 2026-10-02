package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SeekableInMemoryByteChannel#truncate(long)} also pulls back the
 * current position when that position sits beyond the new (smaller) size.
 */
public class SeekableInMemoryByteChannelTest_testSetProperPositionOnTruncate {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void truncateClampsPositionToNewSize() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // Move the position to the very end of the data (index 9 for "Some data").
            channel.position(testData.length);

            // Shrink the channel to 4 bytes; the position now points past the new end.
            final long truncatedSize = 4L;
            channel.truncate(truncatedSize);

            // truncate() must clamp both the size and the now-out-of-range position.
            assertEquals(truncatedSize, channel.position(), "position should be clamped to the truncated size");
            assertEquals(truncatedSize, channel.size(), "size should reflect the truncation");
        }
    }
}
