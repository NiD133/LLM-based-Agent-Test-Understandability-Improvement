package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testSignalEOFWhenPositionAtTheEnd {

    private static final int EOF = -1;

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testSignalEOFWhenPositionAtTheEnd() throws IOException {
        try (SeekableInMemoryByteChannel c = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);

            // Seek past the end of the data to simulate an out-of-bounds position.
            c.position(testData.length + 1);

            // A read from beyond the end must report EOF and leave the buffer untouched.
            final int readCount = c.read(readBuffer);
            assertEquals(0L, readBuffer.position()); // no bytes were written into the buffer
            assertEquals(EOF, readCount);

            // Subsequent reads at the same position must also report EOF.
            assertEquals(EOF, c.read(readBuffer));
        }
    }
}
