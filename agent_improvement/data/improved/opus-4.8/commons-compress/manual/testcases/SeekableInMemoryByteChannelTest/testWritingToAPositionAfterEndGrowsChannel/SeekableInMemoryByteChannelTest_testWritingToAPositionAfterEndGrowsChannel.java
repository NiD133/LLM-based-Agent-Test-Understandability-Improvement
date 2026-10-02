package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testWritingToAPositionAfterEndGrowsChannel {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Verifies the {@link SeekableByteChannel} contract:
     *
     * <q>Setting the position to a value that is greater than the current size is legal but does not change the size of the entity. A later attempt to write
     * bytes at such a position will cause the entity to grow to accommodate the new bytes; the values of any bytes between the previous end-of-file and the
     * newly-written bytes are unspecified.</q>
     *
     * <p>
     * Here we seek 2 bytes past the (empty) end of a freshly opened channel, write {@code testData}, and confirm that the channel grew and that the data we
     * wrote can be read back from the same position.
     * </p>
     */
    @Test
    void testWritingToAPositionAfterEndGrowsChannel() throws Exception {
        final int writeOffset = 2;
        // The channel starts empty; its backing buffer is allocated at the default capacity.
        final int expectedSizeAfterGrowth = 8_192;

        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            // Move the position past the current end-of-file before any data exists.
            channel.position(writeOffset);
            assertEquals(writeOffset, channel.position());

            // Writing at that position must grow the channel to accommodate the new bytes.
            final int bytesWritten = channel.write(ByteBuffer.wrap(testData));
            assertEquals(testData.length, bytesWritten);
            assertEquals(expectedSizeAfterGrowth, channel.size());

            // Read the data back from the position we wrote it to.
            channel.position(writeOffset);
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            channel.read(readBuffer);
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }
}
