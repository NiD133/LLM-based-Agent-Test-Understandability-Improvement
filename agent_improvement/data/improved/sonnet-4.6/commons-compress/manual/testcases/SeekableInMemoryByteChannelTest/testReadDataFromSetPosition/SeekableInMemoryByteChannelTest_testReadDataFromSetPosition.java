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

public class SeekableInMemoryByteChannelTest_testReadDataFromSetPosition {

    // "Some data" has bytes at indices: S(0) o(1) m(2) e(3) ' '(4) d(5) a(6) t(7) a(8)
    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    // The word "data" starts at byte index 5 within "Some data"
    private static final long READ_START_POSITION = 5L;
    // "data" is 4 bytes long
    private static final int BYTES_TO_READ = 4;
    private static final String EXPECTED_CONTENT = "data";

    @Test
    void testReadDataFromSetPosition() throws IOException {
        try (SeekableInMemoryByteChannel c = new SeekableInMemoryByteChannel(testData)) {
            // Seek past "Some " to position the channel at the start of "data"
            c.position(READ_START_POSITION);

            ByteBuffer readBuffer = ByteBuffer.allocate(BYTES_TO_READ);
            final int bytesRead = c.read(readBuffer);

            // All 4 requested bytes should have been read
            assertEquals(BYTES_TO_READ, bytesRead);
            // The bytes read should spell "data"
            assertEquals(EXPECTED_CONTENT, new String(readBuffer.array(), StandardCharsets.UTF_8));
            // After reading, the position should have advanced to the end of the channel
            assertEquals(testData.length, c.position());
        }
    }
}
