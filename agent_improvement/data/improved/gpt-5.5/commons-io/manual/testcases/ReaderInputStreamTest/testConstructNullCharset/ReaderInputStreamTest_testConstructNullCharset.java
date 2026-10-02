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

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharset() throws IOException {
        final Charset defaultCharset = Charset.defaultCharset();
        final Charset nullCharset = null;
        final int minimumBufferSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());

        try (ReaderInputStream in = new ReaderInputStream(new StringReader("ABC"), nullCharset, minimumBufferSize)) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
    }
}
