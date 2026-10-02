package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenNewSizeIsBiggerThanSizeAndPositionIsEvenBigger {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testTruncateMovesPositionWhenNewSizeIsBiggerThanSizeAndPositionIsEvenBigger() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final long originalSize = testData.length;
            final long requestedSizeBeyondOriginalData = testData.length + 1;

            channel.position(2 * testData.length);
            channel.truncate(testData.length + 1);

            assertEquals(originalSize, channel.size());
            assertEquals(requestedSizeBeyondOriginalData, channel.position());
        }
    }
}
