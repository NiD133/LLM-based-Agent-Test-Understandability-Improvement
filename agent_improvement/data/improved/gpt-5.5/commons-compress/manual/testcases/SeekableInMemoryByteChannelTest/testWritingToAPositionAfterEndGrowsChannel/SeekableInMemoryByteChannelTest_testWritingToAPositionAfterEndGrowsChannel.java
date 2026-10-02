package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testWritingToAPositionAfterEndGrowsChannel {

    private static final int WRITE_POSITION_AFTER_END = 2;
    private static final int DEFAULT_GROWN_CHANNEL_SIZE = 8_192;

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testWritingToAPositionAfterEndGrowsChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.position(WRITE_POSITION_AFTER_END);
            assertEquals(WRITE_POSITION_AFTER_END, channel.position());

            final ByteBuffer input = ByteBuffer.wrap(testData);
            assertEquals(testData.length, channel.write(input));
            assertEquals(DEFAULT_GROWN_CHANNEL_SIZE, channel.size());

            channel.position(WRITE_POSITION_AFTER_END);
            final ByteBuffer output = ByteBuffer.allocate(testData.length);
            channel.read(output);

            assertArrayEquals(testData, Arrays.copyOf(output.array(), testData.length));
        }
    }
}
