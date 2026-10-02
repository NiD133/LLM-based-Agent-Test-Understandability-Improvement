package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.channels.ClosedChannelException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SeekableInMemoryByteChannel#truncate(long)} shortens the
 * channel's contents so that only the leading bytes up to the new size remain.
 */
public class SeekableInMemoryByteChannelTest_testTruncateContentsProperly {

    /** Initial channel contents: nine bytes spelling "Some data". */
    private final byte[] initialData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void truncateKeepsOnlyLeadingBytesUpToNewSize() throws ClosedChannelException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(initialData)) {
            // Shrink the channel to its first 4 bytes ("Some").
            final int newSize = 4;
            channel.truncate(newSize);

            // Read back exactly the bytes the channel now reports as its content.
            final byte[] remainingBytes = Arrays.copyOf(channel.array(), (int) channel.size());

            assertEquals("Some", new String(remainingBytes, StandardCharsets.UTF_8));
        }
    }
}
