package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link ReaderInputStream.Builder} handles a {@code null} charset.
 */
public class ReaderInputStreamTest_testResetCharset {

    /**
     * Passing a {@code null} charset to {@link ReaderInputStream.Builder#setCharset(Charset)}
     * should reset the builder to a (non-null) default charset rather than leaving it unset.
     */
    @Test
    void testResetCharset() {
        // Build a reader-input-stream builder, then explicitly clear its charset by passing null.
        final ReaderInputStream.Builder builder = ReaderInputStream.builder()
                .setReader(new StringReader("\uD800"))
                .setCharset((Charset) null);

        // The builder must fall back to a default charset; it never exposes a null charset.
        final Charset resolvedCharset = builder.getCharset();

        assertNotNull(resolvedCharset, "Builder should default to a non-null charset when set to null");
    }
}
