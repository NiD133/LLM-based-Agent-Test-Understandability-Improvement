package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testTruncateDoesntChangeSmallPosition {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /*
     * Per the SeekableByteChannel contract: "If the current position is greater than the given
     * size then it is set to that size." Conversely, if the current position is already less than
     * the truncation size, the position must remain unchanged.
     */
    @Test
    void testTruncateDoesntChangeSmallPosition() throws Exception {
        final long initialPosition = 1;
        final long truncatedSize = testData.length - 1;

        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(initialPosition);
            channel.truncate(truncatedSize);

            assertEquals(truncatedSize, channel.size(),
                    "Channel size should reflect the truncated length");
            assertEquals(initialPosition, channel.position(),
                    "Position should be unchanged when it is smaller than the truncated size");
        }
    }
}
