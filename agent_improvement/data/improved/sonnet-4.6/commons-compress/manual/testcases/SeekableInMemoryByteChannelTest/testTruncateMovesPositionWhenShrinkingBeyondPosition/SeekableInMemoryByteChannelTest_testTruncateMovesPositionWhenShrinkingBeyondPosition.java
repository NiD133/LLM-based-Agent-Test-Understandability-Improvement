package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenShrinkingBeyondPosition {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    // Position placed beyond the truncate target, so truncate must clamp it down to newSize.
    // SeekableByteChannel spec: "if the current position is greater than the given size then it is set to that size."
    @Test
    void testTruncateMovesPositionWhenShrinkingBeyondPosition() throws Exception {
        final long initialPosition = 4; // ahead of the truncate target
        final long truncateSize    = 3; // shrinks the channel so that initialPosition > truncateSize

        try (SeekableByteChannel c = new SeekableInMemoryByteChannel(testData)) {
            c.position(initialPosition);
            c.truncate(truncateSize);
            assertEquals(truncateSize, c.size(),     "channel size must equal the truncate target");
            assertEquals(truncateSize, c.position(), "position must be clamped to the new size when it was beyond it");
        }
    }
}
