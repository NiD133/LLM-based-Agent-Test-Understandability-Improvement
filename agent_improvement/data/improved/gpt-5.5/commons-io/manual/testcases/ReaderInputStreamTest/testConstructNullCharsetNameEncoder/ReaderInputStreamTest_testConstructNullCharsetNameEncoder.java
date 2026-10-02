package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ReaderInputStreamTest_testConstructNullCharsetNameEncoder {

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @SuppressWarnings("deprecation")
    void testConstructNullCharsetNameEncoder() throws IOException {
        final Charset defaultCharset = Charset.defaultCharset();
        final String nullCharsetName = null;
        final int minimumBufferSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());

        try (ReaderInputStream in = new ReaderInputStream(new StringReader("ABC"), nullCharsetName, minimumBufferSize)) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }

        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader("ABC"))
                .setCharset(nullCharsetName)
                .setBufferSize(minimumBufferSize)
                .get()) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
    }
}
