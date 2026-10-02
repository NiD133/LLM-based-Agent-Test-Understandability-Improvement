package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies that passing a {@code null} {@link CharsetEncoder} to the
 * {@link ReaderInputStream} constructor falls back to the JVM's default charset.
 */
public class ReaderInputStreamTest_testConstructNullCharsetEncoder {

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharsetEncoder() throws IOException {
        final Charset defaultCharset = Charset.defaultCharset();
        final CharsetEncoder nullEncoder = null;
        // The buffer must be at least minBufferSize for the chosen encoder.
        final int bufferSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());

        try (ReaderInputStream in =
                new ReaderInputStream(new StringReader("ABC"), nullEncoder, bufferSize)) {
            // Consume the whole stream so the encoder is actually exercised.
            IOUtils.toByteArray(in);

            // A null encoder should resolve to the default charset's encoder.
            assertEquals(defaultCharset, in.getCharsetEncoder().charset());
        }
    }
}
