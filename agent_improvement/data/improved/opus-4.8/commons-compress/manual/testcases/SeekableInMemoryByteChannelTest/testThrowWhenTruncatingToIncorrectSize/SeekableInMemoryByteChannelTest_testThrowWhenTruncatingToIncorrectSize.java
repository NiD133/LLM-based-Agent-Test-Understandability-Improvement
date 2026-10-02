package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowWhenTruncatingToIncorrectSize {

    /**
     * {@link SeekableInMemoryByteChannel#truncate(long)} only shrinks the channel: growing it to a larger size leaves the
     * existing data readable, while a negative target size is rejected with an {@link IllegalArgumentException}.
     */
    @Test
    void testThrowWhenTruncatingToIncorrectSize() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer singleByteBuffer = ByteBuffer.allocate(1);

            // Truncating to a size larger than the current one keeps the channel readable:
            // the default buffer still has at least one byte available to read.
            channel.truncate(channel.size() + 1);
            assertEquals(1, channel.read(singleByteBuffer));

            // Truncating beyond Integer.MAX_VALUE is also accepted; the read returns 0
            // because the single-byte buffer is now full from the previous read.
            channel.truncate(Integer.MAX_VALUE + 1L);
            assertEquals(0, channel.read(singleByteBuffer));

            // Any negative target size is invalid and must be rejected.
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Long.MIN_VALUE));
        }
    }
}
