package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ReaderInputStream} rejects buffer sizes that are too small.
 *
 * <p>
 * The constructor delegates to {@code checkMinBufferSize}, which requires the
 * buffer to hold at least {@code maxBytesPerChar * 2} characters. For UTF-8 that
 * minimum is larger than 1, so buffer sizes of -1, 0, and 1 are all invalid and
 * must trigger an {@link IllegalArgumentException}.
 * </p>
 */
public class ReaderInputStreamTest_testBufferTooSmall {

    /** Buffer sizes that are below the minimum required by the UTF-8 encoder. */
    private static final int[] TOO_SMALL_BUFFER_SIZES = {-1, 0, 1};

    @Test
    void testBufferTooSmall() {
        for (final int bufferSize : TOO_SMALL_BUFFER_SIZES) {
            assertThrows(IllegalArgumentException.class,
                    () -> new ReaderInputStream(new StringReader("\uD800"), StandardCharsets.UTF_8, bufferSize),
                    "Buffer size " + bufferSize + " is below the UTF-8 minimum and must be rejected");
        }
    }
}
