package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testWriteDataProperly {

    /** Sample payload written to the channel under test. */
    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Writing a buffer into a fresh channel should consume all of its bytes,
     * advance the position to the end of the written data, and store exactly
     * those bytes in the backing array.
     */
    @Test
    void testWriteDataProperly() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            // Arrange: wrap the sample payload in a buffer ready to be written.
            final ByteBuffer dataToWrite = ByteBuffer.wrap(testData);

            // Act: write the whole buffer to the channel.
            final int bytesWritten = channel.write(dataToWrite);

            // Assert: every byte was written and the position moved past them.
            assertEquals(testData.length, bytesWritten, "write() should report all bytes as written");
            assertEquals(testData.length, channel.position(), "position should advance to the end of the data");

            // Assert: the backing array holds exactly the bytes we wrote.
            final byte[] storedData = Arrays.copyOf(channel.array(), (int) channel.position());
            assertArrayEquals(testData, storedData, "stored bytes should match the written payload");
        }
    }
}
