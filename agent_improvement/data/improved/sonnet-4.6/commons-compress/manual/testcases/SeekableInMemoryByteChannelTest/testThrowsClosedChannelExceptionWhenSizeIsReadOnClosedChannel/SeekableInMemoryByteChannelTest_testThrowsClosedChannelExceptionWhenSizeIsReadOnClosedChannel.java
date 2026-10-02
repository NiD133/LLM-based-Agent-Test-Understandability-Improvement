package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsClosedChannelExceptionWhenSizeIsReadOnClosedChannel {

    /**
     * Verifies the SeekableByteChannel contract: "ClosedChannelException - If this channel is closed".
     * After explicitly closing the channel, calling size() must throw ClosedChannelException.
     */
    @Test
    void testThrowsClosedChannelExceptionWhenSizeIsReadOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();
            assertThrows(ClosedChannelException.class, channel::size);
        }
    }
}
