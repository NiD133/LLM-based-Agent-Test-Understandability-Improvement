package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsIllegalArgumentExceptionWhenTruncatingToANegativeSize {

    /**
     * Truncating a channel to a negative size is illegal, so
     * {@link SeekableByteChannel#truncate(long)} must reject it with an
     * {@link IllegalArgumentException} rather than silently accepting it.
     */
    @Test
    void truncateToNegativeSizeThrowsIllegalArgumentException() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            final long negativeSize = -1;

            assertThrows(IllegalArgumentException.class, () -> channel.truncate(negativeSize));
        }
    }
}
