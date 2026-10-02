package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsClosedChannelExceptionWhenPositionIsReadOnClosedChannel {

    /**
     * Verifies that calling position() on a closed channel throws ClosedChannelException,
     * as required by the SeekableByteChannel contract.
     */
    @Test
    void testThrowsClosedChannelExceptionWhenPositionIsReadOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();
            assertThrows(ClosedChannelException.class, channel::position);
        }
    }
}
