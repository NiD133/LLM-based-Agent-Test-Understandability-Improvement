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

public class SeekableInMemoryByteChannelTest_testSignalEOFWhenPositionAtTheEnd {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testSignalEOFWhenPositionAtTheEnd() throws IOException {
        try (SeekableInMemoryByteChannel c = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            c.position(testData.length + 1);
            final int readCount = c.read(readBuffer);
            assertEquals(0L, readBuffer.position());
            assertEquals(-1, readCount);
            assertEquals(-1, c.read(readBuffer));
        }
    }
}
