package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ReaderInputStream.Builder#setCharset(String)} falls back to a
 * non-null charset when given a {@code null} charset name.
 */
public class ReaderInputStreamTest_testResetCharsetName {

    /**
     * Passing a {@code null} charset name to the builder should reset it to the
     * default charset rather than leaving it {@code null}, so {@code getCharset()}
     * must still return a usable charset.
     */
    @Test
    void testResetCharsetName() {
        final ReaderInputStream.Builder builder = ReaderInputStream.builder()
                .setReader(new StringReader("\uD800"))
                .setCharset((String) null);

        assertNotNull(builder.getCharset());
    }
}
