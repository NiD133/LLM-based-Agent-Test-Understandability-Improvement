package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ReaderInputStreamTest_testCodingError {

    /**
     * A single, unpaired UTF-16 high surrogate. On its own this is an incomplete
     * character: the encoder treats it as an "underflow" (more input expected),
     * not as a malformed-input error. Reading it should therefore NOT throw.
     */
    private static final String LONE_HIGH_SURROGATE = "\uD800";

    /**
     * Verifies that reading a lone high surrogate does not raise an exception,
     * because the encoder reports underflow rather than a coding error.
     *
     * <p>The same expectation is checked twice: once for the (deprecated)
     * constructor that accepts a {@link CharsetEncoder} directly, and once for
     * the equivalent {@link ReaderInputStream.Builder} configuration.</p>
     */
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingError() throws IOException {
        // Case 1: encoder passed directly to the constructor.
        final CharsetEncoder constructorEncoder = StandardCharsets.UTF_8.newEncoder();
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE), constructorEncoder)) {
            assertDoesNotThrow(() -> in.read(), "Underflow on a lone high surrogate must not throw");
        }

        // Case 2: encoder supplied through the builder.
        final CharsetEncoder builderEncoder = StandardCharsets.UTF_8.newEncoder();
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(LONE_HIGH_SURROGATE))
                .setCharsetEncoder(builderEncoder)
                .get()) {
            assertDoesNotThrow(() -> in.read(), "Underflow on a lone high surrogate must not throw");
        }
    }
}
