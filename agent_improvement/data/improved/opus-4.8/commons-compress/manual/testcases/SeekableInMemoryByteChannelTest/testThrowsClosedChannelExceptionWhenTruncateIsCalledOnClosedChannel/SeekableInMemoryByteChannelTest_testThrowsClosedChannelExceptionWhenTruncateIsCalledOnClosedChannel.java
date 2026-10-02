package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsClosedChannelExceptionWhenTruncateIsCalledOnClosedChannel {

    /**
     * Verifies that calling {@code truncate} on a channel that has already been
     * closed throws a {@link ClosedChannelException}, as required by the
     * {@link SeekableByteChannel} contract.
     */
    @Test
    void truncateOnClosedChannelThrowsClosedChannelException() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();

            assertThrows(ClosedChannelException.class, () -> channel.truncate(0));
        }
    }
}
