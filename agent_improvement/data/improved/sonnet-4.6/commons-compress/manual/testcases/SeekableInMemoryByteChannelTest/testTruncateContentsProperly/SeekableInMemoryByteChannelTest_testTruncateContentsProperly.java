package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateContentsProperly {

    // "Some data" is 9 bytes; truncating to 4 should leave only "Some"
    private static final byte[] INITIAL_CONTENT = "Some data".getBytes(StandardCharsets.UTF_8);
    private static final int TRUNCATE_LENGTH = 4;
    private static final String EXPECTED_CONTENT_AFTER_TRUNCATION = "Some";

    @Test
    void testTruncateContentsProperly() throws ClosedChannelException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(INITIAL_CONTENT)) {

            // Act: truncate the channel to the first 4 bytes
            channel.truncate(TRUNCATE_LENGTH);

            // Assert: the visible content (up to size()) equals the truncated prefix
            byte[] visibleBytes = Arrays.copyOf(channel.array(), (int) channel.size());
            String actualContent = new String(visibleBytes, StandardCharsets.UTF_8);
            assertEquals(EXPECTED_CONTENT_AFTER_TRUNCATION, actualContent);
        }
    }
}
