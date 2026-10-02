package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies that when a {@code null} charset name is supplied, {@link ReaderInputStream}
 * falls back to the JVM's {@linkplain Charset#defaultCharset() default charset}.
 *
 * <p>The fallback is checked through both construction paths:</p>
 * <ul>
 *   <li>the deprecated {@code ReaderInputStream(Reader, String, int)} constructor, and</li>
 *   <li>the {@link ReaderInputStream.Builder} obtained via {@link ReaderInputStream#builder()}.</li>
 * </ul>
 */
public class ReaderInputStreamTest_testConstructNullCharsetNameEncoder {

    /** Arbitrary input text; its content is irrelevant to the charset-fallback assertion. */
    private static final String INPUT_TEXT = "ABC";

    /** A {@code null} charset name must map to the default charset. */
    private static final String NULL_CHARSET_NAME = null;

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharsetNameEncoder() throws IOException {
        final Charset defaultCharset = Charset.defaultCharset();
        // The buffer must be at least large enough for one encoded character.
        final int minBufferSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());

        // Path 1: deprecated constructor with a null charset name.
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(INPUT_TEXT), NULL_CHARSET_NAME, minBufferSize)) {
            IOUtils.toByteArray(in);
            assertEquals(defaultCharset, in.getCharsetEncoder().charset());
        }

        // Path 2: builder configured with the same null charset name.
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(INPUT_TEXT))
                .setCharset(NULL_CHARSET_NAME)
                .setBufferSize(minBufferSize)
                .get()) {
            IOUtils.toByteArray(in);
            assertEquals(defaultCharset, in.getCharsetEncoder().charset());
        }
    }
}
