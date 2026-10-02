package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowWhenTruncatingToIncorrectSize {

    @Test
    void testThrowWhenTruncatingToIncorrectSize() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            // A 1-byte buffer used to verify the channel remains readable after valid truncate calls.
            final ByteBuffer singleByteBuffer = ByteBuffer.allocate(1);

            // Truncating to a size larger than the current size is a no-op: the channel does not
            // grow, but it also does not throw. The channel is still readable (returns 1 byte from
            // the default-allocated internal buffer).
            channel.truncate(channel.size() + 1);
            assertEquals(1, channel.read(singleByteBuffer));

            // Truncating to a value that exceeds Integer.MAX_VALUE is also silently ignored.
            // The 1-byte buffer is now full (0 remaining), so read returns 0, not -1.
            channel.truncate(Integer.MAX_VALUE + 1L);
            assertEquals(0, channel.read(singleByteBuffer));

            // Any negative size is invalid: truncate must throw IllegalArgumentException.
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Long.MIN_VALUE));
        }
    }
}
