package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

/**
 * Verifies that calling {@link SeekableByteChannel#close()} more than once on a
 * {@link SeekableInMemoryByteChannel} is safe and behaves as a no-op after the
 * first call.
 *
 * <p>This matches the {@link java.nio.channels.Channel#close()} contract:
 * "If the stream is already closed then invoking this method has no effect."</p>
 */
public class SeekableInMemoryByteChannelTest_testCloseIsIdempotent {

    @Test
    void testCloseIsIdempotent() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            // First close: the channel transitions to the closed state.
            channel.close();
            assertFalse(channel.isOpen(), "channel should be closed after the first close()");

            // Second close: must have no effect; the channel stays closed.
            channel.close();
            assertFalse(channel.isOpen(), "a redundant close() must leave the channel closed");
        }
    }
}
