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

public class SeekableInMemoryByteChannelTest_testTruncateToCurrentSizeDoesntChangeAnything {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /*
     * <q>If the given size is greater than or equal to the current size then the entity is not modified.</q>
     */
    @Test
    void testTruncateToCurrentSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel c = new SeekableInMemoryByteChannel(testData)) {
            assertEquals(testData.length, c.size());
            c.truncate(testData.length);
            assertEquals(testData.length, c.size());
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            assertEquals(testData.length, c.read(readBuffer));
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }
}
