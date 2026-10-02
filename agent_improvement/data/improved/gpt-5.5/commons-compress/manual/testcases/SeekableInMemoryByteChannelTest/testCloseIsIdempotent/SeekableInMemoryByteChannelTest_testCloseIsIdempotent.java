package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testCloseIsIdempotent {

    /*
     * <q>If the stream is already closed then invoking this method has no effect.</q>
     */
    @Test
    void testCloseIsIdempotent() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();
            assertFalse(channel.isOpen());

            channel.close();
            assertFalse(channel.isOpen());
        }
    }
}
