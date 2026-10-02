package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.channels.ClosedChannelException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateContentsProperly {

    private static final String ORIGINAL_TEXT = "Some data";
    private static final String TEXT_AFTER_TRUNCATION = "Some";

    private final byte[] testData = ORIGINAL_TEXT.getBytes(StandardCharsets.UTF_8);

    @Test
    void testTruncateContentsProperly() throws ClosedChannelException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.truncate(4);

            final byte[] truncatedBytes = Arrays.copyOf(channel.array(), (int) channel.size());
            final String truncatedText = new String(truncatedBytes, StandardCharsets.UTF_8);

            assertEquals(TEXT_AFTER_TRUNCATION, truncatedText);
        }
    }
}
