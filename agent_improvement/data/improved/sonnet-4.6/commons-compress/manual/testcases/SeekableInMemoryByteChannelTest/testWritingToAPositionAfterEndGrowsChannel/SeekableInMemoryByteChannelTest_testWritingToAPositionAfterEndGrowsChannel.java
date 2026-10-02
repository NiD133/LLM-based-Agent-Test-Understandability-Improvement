package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testWritingToAPositionAfterEndGrowsChannel {

    // The default no-arg constructor pre-allocates IOUtils.DEFAULT_BUFFER_SIZE (8192) bytes,
    // so the logical size starts at 8192 even before any data is written.
    private static final int DEFAULT_CHANNEL_SIZE = 8_192;

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * From the SeekableByteChannel specification:
     * "Setting the position to a value that is greater than the current size is legal but does
     * not change the size of the entity. A later attempt to write bytes at such a position will
     * cause the entity to grow to accommodate the new bytes; the values of any bytes between the
     * previous end-of-file and the newly-written bytes are unspecified."
     *
     * This test verifies that positioning the channel past the logical end and then writing
     * leaves the channel size intact (or grown) and that the written data can be read back
     * from the same offset.
     */
    @Test
    void testWritingToAPositionAfterEndGrowsChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {

            // Position past the start (simulates seeking after end of written content)
            channel.position(2);
            assertEquals(2, channel.position());

            // Write the test payload at offset 2
            final ByteBuffer dataToWrite = ByteBuffer.wrap(testData);
            assertEquals(testData.length, channel.write(dataToWrite));

            // The channel size stays at the pre-allocated default because the write (offset 2
            // + 9 bytes = 11 bytes) fits within the initial 8192-byte buffer
            assertEquals(DEFAULT_CHANNEL_SIZE, channel.size());

            // Seek back to the write offset and verify the data round-trips correctly
            channel.position(2);
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            channel.read(readBuffer);
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }
}
