package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ReaderInputStreamTest_testBufferSmallest {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static final String MALFORMED_SURROGATE = "\uD800";

    private static final int MINIMUM_UTF_8_BUFFER_SIZE =
            (int) ReaderInputStream.minBufferSize(UTF_8.newEncoder());

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testBufferSmallest() throws IOException {
        try (InputStream in = new ReaderInputStream(new StringReader(MALFORMED_SURROGATE), UTF_8, MINIMUM_UTF_8_BUFFER_SIZE)) {
            in.read();
        }

        try (InputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(MALFORMED_SURROGATE))
                .setCharset(UTF_8)
                .setBufferSize(MINIMUM_UTF_8_BUFFER_SIZE)
                .get()) {
            in.read();
        }
    }
}
