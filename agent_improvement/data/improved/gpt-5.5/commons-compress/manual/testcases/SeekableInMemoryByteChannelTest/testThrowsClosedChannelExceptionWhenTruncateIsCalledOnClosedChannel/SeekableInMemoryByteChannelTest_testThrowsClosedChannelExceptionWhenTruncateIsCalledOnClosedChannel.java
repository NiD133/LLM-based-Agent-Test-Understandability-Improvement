package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsClosedChannelExceptionWhenTruncateIsCalledOnClosedChannel {

    @Test
    void testThrowsClosedChannelExceptionWhenTruncateIsCalledOnClosedChannel() throws Exception {
        try (SeekableByteChannel closedChannel = new SeekableInMemoryByteChannel()) {
            closedChannel.close();

            assertThrows(ClosedChannelException.class, () -> closedChannel.truncate(0));
        }
    }
}
