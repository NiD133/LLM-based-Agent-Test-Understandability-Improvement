package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

/**
 * Verifies the truncation contract of {@link SeekableInMemoryByteChannel}.
 *
 * <p>According to {@link SeekableByteChannel#truncate(long)}: the current
 * position is only moved back when it is greater than the new size. When the
 * position already sits below the new size, truncation must leave it
 * untouched.</p>
 */
public class SeekableInMemoryByteChannelTest_testTruncateDoesntChangeSmallPosition {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testTruncateDoesntChangeSmallPosition() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // Place the position near the start, well below the size we will truncate to.
            final int positionWithinNewSize = 1;
            final int newSize = testData.length - 1;

            channel.position(positionWithinNewSize);
            channel.truncate(newSize);

            // The channel shrinks to the requested size...
            assertEquals(newSize, channel.size());
            // ...but the position stays put because it was already smaller than the new size.
            assertEquals(positionWithinNewSize, channel.position());
        }
    }
}
