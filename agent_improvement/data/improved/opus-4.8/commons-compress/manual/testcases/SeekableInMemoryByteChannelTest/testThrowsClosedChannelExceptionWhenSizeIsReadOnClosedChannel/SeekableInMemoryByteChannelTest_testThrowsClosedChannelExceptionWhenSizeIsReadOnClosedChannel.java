package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsClosedChannelExceptionWhenSizeIsReadOnClosedChannel {

    /**
     * Calling {@link SeekableByteChannel#size()} after the channel has been closed must fail with a
     * {@link ClosedChannelException}, as documented by the {@code SeekableByteChannel} contract.
     */
    @Test
    void sizeOnClosedChannelThrowsClosedChannelException() throws Exception {
        final SeekableByteChannel channel = new SeekableInMemoryByteChannel();

        channel.close();

        assertThrows(ClosedChannelException.class, channel::size);
    }
}
