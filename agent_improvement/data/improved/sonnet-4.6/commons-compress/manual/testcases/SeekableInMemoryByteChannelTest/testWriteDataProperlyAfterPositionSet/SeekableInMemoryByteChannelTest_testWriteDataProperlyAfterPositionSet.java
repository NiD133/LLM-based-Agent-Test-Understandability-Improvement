package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testWriteDataProperlyAfterPositionSet {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testWriteDataProperlyAfterPositionSet() throws IOException {
        // Seek to offset 5 and write testData, so the first 5 bytes of the
        // channel's initial content are preserved and bytes from offset 5 onward
        // are overwritten / extended by the incoming data.
        final int writeOffset = 5;
        final int expectedSize = writeOffset + testData.length;

        // Expected layout: original first 5 bytes, then testData written at offset 5.
        byte[] expectedContent = new byte[expectedSize];
        System.arraycopy(testData, 0, expectedContent, 0, writeOffset);
        System.arraycopy(testData, 0, expectedContent, writeOffset, testData.length);

        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(writeOffset);
            int bytesWritten = channel.write(ByteBuffer.wrap(testData));

            assertEquals(testData.length, bytesWritten,
                    "All bytes of the source buffer should have been written");
            assertArrayEquals(expectedContent, Arrays.copyOf(channel.array(), (int) channel.size()),
                    "Channel content should be the original prefix followed by the written data");
            assertEquals(expectedSize, channel.position(),
                    "Position should advance to writeOffset + number of bytes written");
        }
    }
}
