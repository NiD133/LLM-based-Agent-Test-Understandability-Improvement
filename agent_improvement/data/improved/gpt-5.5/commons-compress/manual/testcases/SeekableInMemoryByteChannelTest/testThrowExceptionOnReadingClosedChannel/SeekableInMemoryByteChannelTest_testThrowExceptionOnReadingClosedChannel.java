package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowExceptionOnReadingClosedChannel {

    @Test
    void testThrowExceptionOnReadingClosedChannel() {
        final SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.close();

        assertThrows(ClosedChannelException.class, () -> channel.read(ByteBuffer.allocate(1)));
    }
}
