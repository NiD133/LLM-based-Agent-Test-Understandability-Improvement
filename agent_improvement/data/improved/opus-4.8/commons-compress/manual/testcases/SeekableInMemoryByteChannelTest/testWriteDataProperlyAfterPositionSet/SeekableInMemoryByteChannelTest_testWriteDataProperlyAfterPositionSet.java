package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testWriteDataProperlyAfterPositionSet {

    /** Payload used both as the channel's initial content and as the bytes written into it. */
    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Writing at a non-zero position should overwrite from that offset and extend the channel,
     * leaving the original prefix intact in front of the newly written bytes.
     */
    @Test
    void testWriteDataProperlyAfterPositionSet() throws IOException {
        final int writeOffset = 5;

        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer dataToWrite = ByteBuffer.wrap(testData);

            // After writing testData at offset 5, the contents are:
            // the first 5 bytes of the original data, followed by the full testData.
            final ByteBuffer expectedContents = ByteBuffer.allocate(testData.length + writeOffset)
                    .put(testData, 0, writeOffset)
                    .put(testData);

            channel.position(writeOffset);
            final int bytesWritten = channel.write(dataToWrite);

            // The whole buffer is written.
            assertEquals(testData.length, bytesWritten);
            // The channel holds the original prefix followed by the written data.
            assertArrayEquals(expectedContents.array(), Arrays.copyOf(channel.array(), (int) channel.size()));
            // The position advances past the bytes just written.
            assertEquals(testData.length + writeOffset, channel.position());
        }
    }
}
