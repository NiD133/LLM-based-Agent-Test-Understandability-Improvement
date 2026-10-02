package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testBufferTooSmall {

    // The minimum valid buffer size for UTF-8 is ceil(maxBytesPerChar * 2) = 6.
    // Buffer sizes <= 1 must be rejected with IllegalArgumentException.
    private static final int[] INVALID_BUFFER_SIZES = { -1, 0, 1 };

    @Test
    void testBufferTooSmall() {
        for (int bufferSize : INVALID_BUFFER_SIZES) {
            final int size = bufferSize;
            assertThrows(IllegalArgumentException.class,
                    () -> new ReaderInputStream(new StringReader("\uD800"), StandardCharsets.UTF_8, size),
                    "Expected IllegalArgumentException for buffer size " + size);
        }
    }
}
