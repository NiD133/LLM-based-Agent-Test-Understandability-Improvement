package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Tests that {@link ReaderInputStream} works when configured with the smallest buffer size the
 * charset encoder will accept.
 *
 * <p>The minimum buffer size is {@code maxBytesPerChar * 2} (see
 * {@link ReaderInputStream#minBufferSize}). Using a lone high surrogate as input also exercises the
 * encoder's malformed-input handling at this boundary. The test only needs to confirm that reading
 * completes without hanging or throwing, hence the timeout.</p>
 */
public class ReaderInputStreamTest_testBufferSmallest {

    /** Charset under test; its encoder determines the smallest legal buffer size. */
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /** A lone, unpaired UTF-16 high surrogate (malformed when encoded on its own). */
    private static final String LONE_HIGH_SURROGATE = "\uD800";

    /** Smallest buffer size accepted by {@link ReaderInputStream} for {@link #CHARSET}. */
    private static final int SMALLEST_BUFFER_SIZE = (int) ReaderInputStream.minBufferSize(CHARSET.newEncoder());

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testBufferSmallest() throws IOException {
        // The deprecated constructor and the Builder must both behave the same way at the
        // smallest buffer size: a single read must complete without error.
        try (InputStream in = new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE), CHARSET, SMALLEST_BUFFER_SIZE)) {
            in.read();
        }
        try (InputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(LONE_HIGH_SURROGATE))
                .setCharset(CHARSET)
                .setBufferSize(SMALLEST_BUFFER_SIZE)
                .get()) {
            in.read();
        }
    }
}
