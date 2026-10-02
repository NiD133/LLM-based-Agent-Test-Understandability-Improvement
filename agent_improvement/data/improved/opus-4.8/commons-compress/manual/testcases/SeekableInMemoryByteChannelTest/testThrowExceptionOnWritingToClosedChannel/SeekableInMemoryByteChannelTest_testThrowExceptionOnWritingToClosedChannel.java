package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowExceptionOnWritingToClosedChannel {

    @Test
    void testThrowExceptionOnWritingToClosedChannel() {
        // Given a channel that has been closed
        final SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.close();

        // When attempting to write to it, a ClosedChannelException is thrown
        assertThrows(ClosedChannelException.class, () -> channel.write(ByteBuffer.allocate(1)));
    }
}
