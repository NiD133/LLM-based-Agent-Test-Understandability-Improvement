package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateDoesntChangeSmallPosition {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testTruncateDoesntChangeSmallPosition() throws Exception {
        final int positionWithinTruncatedSize = 1;
        final int truncatedSize = testData.length - 1;

        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(positionWithinTruncatedSize);

            channel.truncate(truncatedSize);

            assertEquals(truncatedSize, channel.size());
            assertEquals(positionWithinTruncatedSize, channel.position());
        }
    }
}
