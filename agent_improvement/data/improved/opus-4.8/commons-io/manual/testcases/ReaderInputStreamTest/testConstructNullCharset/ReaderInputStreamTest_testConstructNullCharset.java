package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ReaderInputStreamTest_testConstructNullCharset {

    /**
     * When the {@link java.nio.charset.CharsetEncoder} argument is {@code null}, the stream must fall back to the JVM's
     * default charset (see the {@code charsetEncoder, null defaults to the default Charset encoder} contract on the
     * {@code ReaderInputStream(Reader, CharsetEncoder, int)} constructor).
     */
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharset() throws IOException {
        final Charset defaultCharset = Charset.defaultCharset();
        final Charset nullEncoder = null;
        final int bufferSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());

        try (ReaderInputStream in = new ReaderInputStream(new StringReader("ABC"), nullEncoder, bufferSize)) {
            // Drain the stream so the encoder is actually exercised.
            IOUtils.toByteArray(in);

            // A null encoder must resolve to one backed by the default charset.
            assertEquals(defaultCharset, in.getCharsetEncoder().charset());
        }
    }
}
