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

    /**
     * A lone high surrogate (U+D800) with no matching low surrogate. This is malformed input
     * for any charset, so an encoder configured with {@link CodingErrorAction#REPORT} must
     * raise a {@link CharacterCodingException} rather than silently replacing it.
     */
    private static final String MALFORMED_INPUT = "\uD800aa";

    /**
     * Tests IO-717 to avoid infinite loops.
     *
     * <p>
     * When the {@link CharsetEncoder} is configured to {@link CodingErrorAction#REPORT} malformed
     * input, reading from a {@link ReaderInputStream} backed by malformed input must throw a
     * {@link CharacterCodingException} on the first {@code read()}. The {@link Timeout} guards
     * against a regression where the stream would loop forever instead of reporting the error.
     * </p>
     *
     * <p>
     * The same scenario is exercised twice: once via the deprecated constructor and once via the
     * {@link ReaderInputStream.Builder}, to confirm both construction paths report the error.
     * </p>
     */
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingErrorAction() throws IOException {
        final Charset charset = StandardCharsets.UTF_8;
        final CharsetEncoder reportingEncoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPORT);
        final int minBufferSize = (int) ReaderInputStream.minBufferSize(reportingEncoder);

        // Path 1: construct directly via the (deprecated) constructor.
        try (InputStream in = new ReaderInputStream(new StringReader(MALFORMED_INPUT), reportingEncoder, minBufferSize)) {
            assertThrows(CharacterCodingException.class, in::read);
        }

        // Path 2: construct via the builder.
        try (InputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(MALFORMED_INPUT))
                .setCharsetEncoder(reportingEncoder)
                .setBufferSize((int) ReaderInputStream.minBufferSize(charset.newEncoder()))
                .get()) {
            assertThrows(CharacterCodingException.class, in::read);
        }
    }
}
