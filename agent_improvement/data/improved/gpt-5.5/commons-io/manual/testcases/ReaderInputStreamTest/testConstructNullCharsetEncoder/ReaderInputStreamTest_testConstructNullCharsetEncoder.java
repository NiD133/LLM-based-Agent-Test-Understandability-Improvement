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

public class ReaderInputStreamTest_testConstructNullCharsetEncoder {

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharsetEncoder() throws IOException {
        final Charset charsetForMinimumBufferSize = Charset.defaultCharset();
        final CharsetEncoder nullEncoder = null;

        try (ReaderInputStream in = new ReaderInputStream(new StringReader("ABC"), nullEncoder,
                (int) ReaderInputStream.minBufferSize(charsetForMinimumBufferSize.newEncoder()))) {
            IOUtils.toByteArray(in);

            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
    }
}
