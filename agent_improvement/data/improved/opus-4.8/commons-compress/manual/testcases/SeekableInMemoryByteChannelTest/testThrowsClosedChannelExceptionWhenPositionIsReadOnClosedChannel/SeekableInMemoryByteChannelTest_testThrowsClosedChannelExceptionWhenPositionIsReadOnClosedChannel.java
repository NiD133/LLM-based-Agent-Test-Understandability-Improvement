package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsClosedChannelExceptionWhenPositionIsReadOnClosedChannel {

    /**
     * Reading the position of a closed channel must fail with a
     * {@link ClosedChannelException}, as documented on
     * {@link SeekableByteChannel#position()}.
     */
    @Test
    void testThrowsClosedChannelExceptionWhenPositionIsReadOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();

            assertThrows(ClosedChannelException.class, channel::position);
        }
    }
}
