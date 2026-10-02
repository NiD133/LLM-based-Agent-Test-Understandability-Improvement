package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testCloseIsIdempotent {

    /**
     * Verifies that closing an already-closed channel is safe and has no effect,
     * as required by the {@link java.io.Closeable} contract:
     * "If the stream is already closed then invoking this method has no effect."
     */
    @Test
    void testCloseIsIdempotent() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            // First close: transitions the channel from open to closed
            channel.close();
            assertFalse(channel.isOpen(), "Channel should be closed after first close()");

            // Second close: must not throw and channel must remain closed
            channel.close();
            assertFalse(channel.isOpen(), "Channel should remain closed after second close()");
        }
    }
}
