package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize {

    private static final byte[] TEST_DATA = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(TEST_DATA)) {
            channel.position(2 * TEST_DATA.length);

            channel.truncate(TEST_DATA.length);

            assertEquals(TEST_DATA.length, channel.size());
            assertEquals(TEST_DATA.length, channel.position());
        }
    }
}
