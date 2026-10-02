package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsClosedChannelExceptionWhenPositionIsSetOnClosedChannel {

    /**
     * Setting the position on a channel that has been closed must fail with a
     * {@link ClosedChannelException}, as documented by {@link SeekableByteChannel}.
     */
    @Test
    void testThrowsClosedChannelExceptionWhenPositionIsSetOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();

            assertThrows(ClosedChannelException.class, () -> channel.position(0));
        }
    }
}
