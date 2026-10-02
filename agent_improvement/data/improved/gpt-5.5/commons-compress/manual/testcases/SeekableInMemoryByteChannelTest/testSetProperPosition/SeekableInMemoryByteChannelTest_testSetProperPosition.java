package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testSetProperPosition {

    private static final long POSITION_WITHIN_DATA = 4L;

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testSetProperPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final long positionWithinData = channel.position(POSITION_WITHIN_DATA).position();
            final long positionAtEndOfData = channel.position(testData.length).position();
            final long positionPastEndOfData = channel.position(testData.length + 1L).position();

            assertEquals(POSITION_WITHIN_DATA, positionWithinData);
            assertEquals(channel.size(), positionAtEndOfData);
            assertEquals(testData.length + 1L, positionPastEndOfData);
        }
    }
}
