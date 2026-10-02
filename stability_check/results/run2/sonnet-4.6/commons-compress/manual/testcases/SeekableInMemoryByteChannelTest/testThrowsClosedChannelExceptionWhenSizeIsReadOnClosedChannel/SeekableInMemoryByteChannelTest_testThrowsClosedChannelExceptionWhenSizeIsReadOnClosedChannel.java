package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

/**
 * Tests that SeekableInMemoryByteChannel enforces the closed-channel contract
 * defined by SeekableByteChannel: operations on a closed channel must throw
 * ClosedChannelException.
 */
public class SeekableInMemoryByteChannelTest_testThrowsClosedChannelExceptionWhenSizeIsReadOnClosedChannel {

    @Test
    void testThrowsClosedChannelExceptionWhenSizeIsReadOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            // Explicitly close the channel before invoking size() to trigger the closed-channel guard
            channel.close();
            assertThrows(ClosedChannelException.class, channel::size);
        }
    }
}
