package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowExceptionOnWritingToClosedChannel {

    @Test
    void testThrowExceptionOnWritingToClosedChannel() {
        final SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.close();

        // Writing to an explicitly closed channel must raise ClosedChannelException.
        final ByteBuffer oneByte = ByteBuffer.allocate(1);
        assertThrows(ClosedChannelException.class, () -> channel.write(oneByte));
    }
}
