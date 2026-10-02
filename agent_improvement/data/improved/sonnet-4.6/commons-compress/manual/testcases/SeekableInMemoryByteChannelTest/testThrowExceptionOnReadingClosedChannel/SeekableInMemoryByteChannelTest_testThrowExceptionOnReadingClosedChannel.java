package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowExceptionOnReadingClosedChannel {

    @Test
    void testThrowExceptionOnReadingClosedChannel() {
        final SeekableInMemoryByteChannel closedChannel = new SeekableInMemoryByteChannel();
        closedChannel.close();
        assertThrows(ClosedChannelException.class, () -> closedChannel.read(ByteBuffer.allocate(1)));
    }
}
