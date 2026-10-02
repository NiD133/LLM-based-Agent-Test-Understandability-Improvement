package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateToBiggerSizeDoesntChangeAnything {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Per the {@link SeekableByteChannel#truncate(long)} contract: "If the given size is greater than or equal to the
     * current size then the entity is not modified." This verifies that truncating to a size larger than the current
     * content leaves both the reported size and the stored bytes untouched.
     */
    @Test
    void testTruncateToBiggerSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // The channel starts out holding exactly the test data.
            assertEquals(testData.length, channel.size());

            // Truncating to a size larger than the content must be a no-op.
            channel.truncate(testData.length + 1);
            assertEquals(testData.length, channel.size(), "size must be unchanged after truncating to a bigger size");

            // The original bytes must still be readable and intact.
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            assertEquals(testData.length, channel.read(readBuffer), "all original bytes should be read back");
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length),
                    "the stored content must be identical to the original data");
        }
    }
}
