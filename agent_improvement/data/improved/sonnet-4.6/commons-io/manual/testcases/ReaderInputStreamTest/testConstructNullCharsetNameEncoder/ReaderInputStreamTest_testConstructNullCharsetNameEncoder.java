package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testConstructNullCharsetNameEncoder {

    @Test
    void testConstructNullCharsetNameEncoder() throws IOException {
        final String nullCharsetName = null;
        final Charset defaultCharset = Charset.defaultCharset();
        final int bufferSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());

        // Verify that a null charset name falls back to the JVM default charset — via deprecated constructor
        try (ReaderInputStream in = new ReaderInputStream(new StringReader("ABC"), nullCharsetName, bufferSize)) {
            IOUtils.toByteArray(in);
            assertEquals(defaultCharset, in.getCharsetEncoder().charset());
        }

        // Verify the same fallback behaviour when using the builder API
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader("ABC"))
                .setCharset(nullCharsetName)
                .setBufferSize(bufferSize)
                .get()) {
            IOUtils.toByteArray(in);
            assertEquals(defaultCharset, in.getCharsetEncoder().charset());
        }
    }
}
