package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testSetProperPosition {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testSetProperPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {

            // Position within the data (index 4, well before the end)
            long positionWithinData = channel.position(4L).position();

            // Position exactly at the end of the data (equals size())
            long positionAtEnd = channel.position(testData.length).position();

            // Position one step past the end (channel allows seeking beyond size)
            long positionPastEnd = channel.position(testData.length + 1L).position();

            assertEquals(4L, positionWithinData);
            assertEquals(channel.size(), positionAtEnd);
            assertEquals(testData.length + 1L, positionPastEnd);
        }
    }
}
