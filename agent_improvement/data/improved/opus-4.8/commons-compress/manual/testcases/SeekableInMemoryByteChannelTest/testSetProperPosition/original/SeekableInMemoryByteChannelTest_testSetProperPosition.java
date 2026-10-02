package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SeekableInMemoryByteChannelTest_testSetProperPosition {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testSetProperPosition() throws IOException {
        try (SeekableInMemoryByteChannel c = new SeekableInMemoryByteChannel(testData)) {
            final long posAtFour = c.position(4L).position();
            final long posAtTheEnd = c.position(testData.length).position();
            final long posPastTheEnd = c.position(testData.length + 1L).position();
            assertEquals(4L, posAtFour);
            assertEquals(c.size(), posAtTheEnd);
            assertEquals(testData.length + 1L, posPastTheEnd);
        }
    }
}
