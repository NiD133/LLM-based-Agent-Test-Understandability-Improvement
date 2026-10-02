package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testSignalEOFWhenPositionAtTheEnd {

    private static final byte[] CHANNEL_CONTENT = "Some data".getBytes(StandardCharsets.UTF_8);

    private static final int EOF = -1;

    /**
     * When the channel position is moved past the end of the data, every read must
     * report end-of-stream (-1) and must leave the destination buffer untouched.
     */
    @Test
    void testSignalEOFWhenPositionAtTheEnd() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(CHANNEL_CONTENT)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(CHANNEL_CONTENT.length);

            // Move the position one byte beyond the last byte of the data.
            channel.position(CHANNEL_CONTENT.length + 1);

            final int firstReadCount = channel.read(readBuffer);

            assertEquals(EOF, firstReadCount, "read past the end should signal EOF");
            assertEquals(0L, readBuffer.position(), "no bytes should be written into the buffer");

            // A subsequent read still reports EOF.
            assertEquals(EOF, channel.read(readBuffer), "repeated read past the end should signal EOF");
        }
    }
}
