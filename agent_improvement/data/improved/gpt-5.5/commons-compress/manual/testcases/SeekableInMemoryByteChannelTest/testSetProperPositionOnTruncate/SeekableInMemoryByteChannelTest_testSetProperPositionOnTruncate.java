package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testSetProperPositionOnTruncate {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testSetProperPositionOnTruncate() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(testData.length);
            channel.truncate(4L);

            assertEquals(4L, channel.position());
            assertEquals(4L, channel.size());
        }
    }
}
