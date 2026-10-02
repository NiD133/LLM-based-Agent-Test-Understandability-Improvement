package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsIOExceptionWhenPositionIsSetToANegativeValue {

    /**
     * Setting the channel position to a negative value is illegal and must be rejected.
     * As documented for {@link SeekableByteChannel#position(long)}, a negative position
     * results in an {@link IllegalArgumentException}.
     */
    @Test
    void rejectsNegativePositionWithIllegalArgumentException() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            final long negativePosition = -1;
            assertThrows(IllegalArgumentException.class, () -> channel.position(negativePosition));
        }
    }
}
