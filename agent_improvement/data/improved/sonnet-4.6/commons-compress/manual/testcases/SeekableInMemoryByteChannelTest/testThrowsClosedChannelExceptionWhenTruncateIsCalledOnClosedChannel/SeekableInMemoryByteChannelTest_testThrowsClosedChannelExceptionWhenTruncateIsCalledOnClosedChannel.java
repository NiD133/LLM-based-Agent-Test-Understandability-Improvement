package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsClosedChannelExceptionWhenTruncateIsCalledOnClosedChannel {

    @Test
    void testThrowsClosedChannelExceptionWhenTruncateIsCalledOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            // Close the channel explicitly before calling truncate
            channel.close();

            // Calling truncate on a closed channel must throw ClosedChannelException
            assertThrows(ClosedChannelException.class, () -> channel.truncate(0));
        }
    }
}
