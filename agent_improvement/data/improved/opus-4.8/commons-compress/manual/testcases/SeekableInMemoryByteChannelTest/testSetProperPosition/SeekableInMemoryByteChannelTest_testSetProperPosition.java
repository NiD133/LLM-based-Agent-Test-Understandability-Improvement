package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SeekableInMemoryByteChannel#position(long)} stores whatever
 * non-negative position it is given and reports it back unchanged via
 * {@link SeekableInMemoryByteChannel#position()} - including positions at the very end
 * of the data and beyond it.
 */
public class SeekableInMemoryByteChannelTest_testSetProperPosition {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testSetProperPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // A position within the data is reported back exactly.
            final long positionInsideData = channel.position(4L).position();
            assertEquals(4L, positionInsideData);

            // A position at the end of the data equals the channel size.
            final long positionAtEnd = channel.position(testData.length).position();
            assertEquals(channel.size(), positionAtEnd);

            // A position past the end is accepted and reported back unchanged.
            final long positionPastEnd = channel.position(testData.length + 1L).position();
            assertEquals(testData.length + 1L, positionPastEnd);
        }
    }
}
