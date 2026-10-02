package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowExceptionOnWritingToClosedChannel {

    @Test
    void testThrowExceptionOnWritingToClosedChannel() {
        final SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        final ByteBuffer oneByteToWrite = ByteBuffer.allocate(1);

        channel.close();

        assertThrows(ClosedChannelException.class, () -> channel.write(oneByteToWrite));
    }
}
