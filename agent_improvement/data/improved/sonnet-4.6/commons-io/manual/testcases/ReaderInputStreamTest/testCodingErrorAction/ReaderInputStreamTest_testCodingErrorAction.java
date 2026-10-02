package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ReaderInputStreamTest_testCodingErrorAction {

    // \uD800 is a lone high surrogate — malformed UTF-8 input that triggers REPORT action
    private static final String MALFORMED_SURROGATE_INPUT = "\uD800aa";

    /**
     * Tests IO-717: ReaderInputStream with CodingErrorAction.REPORT must throw
     * CharacterCodingException on malformed input rather than looping infinitely.
     */
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingErrorAction() throws IOException {
        final Charset charset = StandardCharsets.UTF_8;
        final CharsetEncoder encoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPORT);

        // Test via deprecated direct constructor
        final int minBufferSize = (int) ReaderInputStream.minBufferSize(encoder);
        try (InputStream in = new ReaderInputStream(new StringReader(MALFORMED_SURROGATE_INPUT), encoder, minBufferSize)) {
            assertThrows(CharacterCodingException.class, in::read);
        }

        // Test via builder API (reuses the same encoder; buffer size derived from a fresh encoder)
        final int minBufferSizeForBuilder = (int) ReaderInputStream.minBufferSize(charset.newEncoder());
        try (InputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(MALFORMED_SURROGATE_INPUT))
                .setCharsetEncoder(encoder)
                .setBufferSize(minBufferSizeForBuilder)
                .get()) {
            assertThrows(CharacterCodingException.class, in::read);
        }
    }
}
