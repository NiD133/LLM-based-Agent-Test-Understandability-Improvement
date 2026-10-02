package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testWriteDataProperly {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testWriteDataProperly() throws IOException {
        // Arrange: open a fresh in-memory channel and wrap the test bytes in a ByteBuffer
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            ByteBuffer sourceBuffer = ByteBuffer.wrap(testData);

            // Act: write all bytes from the buffer into the channel
            int bytesWritten = channel.write(sourceBuffer);

            // Assert: every byte was written, the position advanced accordingly,
            // and the channel's backing array holds exactly the written bytes
            assertEquals(testData.length, bytesWritten,
                    "write() should return the number of bytes actually written");
            assertEquals(testData.length, channel.position(),
                    "channel position should advance by the number of bytes written");
            assertArrayEquals(testData, Arrays.copyOf(channel.array(), (int) channel.position()),
                    "the backing array should contain exactly the bytes that were written");
        }
    }
}
