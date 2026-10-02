package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testWriteDataProperlyAfterPositionSet {

    private static final long WRITE_START_POSITION = 5L;
    private static final int UNCHANGED_PREFIX_LENGTH = 5;

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testWriteDataProperlyAfterPositionSet() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer dataToWrite = ByteBuffer.wrap(testData);
            final ByteBuffer expectedData = ByteBuffer.allocate(testData.length + UNCHANGED_PREFIX_LENGTH)
                    .put(testData, 0, UNCHANGED_PREFIX_LENGTH)
                    .put(testData);

            channel.position(WRITE_START_POSITION);
            final int writeCount = channel.write(dataToWrite);

            assertEquals(testData.length, writeCount);
            assertArrayEquals(expectedData.array(), Arrays.copyOf(channel.array(), (int) channel.size()));
            assertEquals(testData.length + WRITE_START_POSITION, channel.position());
        }
    }
}
