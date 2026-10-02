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

public class ReaderInputStreamTest_testConstructNullCharset {

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharset() throws IOException {
        // When a null CharsetEncoder is supplied, ReaderInputStream falls back to the default charset.
        final Charset defaultCharset = Charset.defaultCharset();
        final CharsetEncoder nullEncoder = null;
        final int bufferSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());

        try (ReaderInputStream in = new ReaderInputStream(new StringReader("ABC"), nullEncoder, bufferSize)) {
            IOUtils.toByteArray(in);
            assertEquals(defaultCharset, in.getCharsetEncoder().charset());
        }
    }
}
