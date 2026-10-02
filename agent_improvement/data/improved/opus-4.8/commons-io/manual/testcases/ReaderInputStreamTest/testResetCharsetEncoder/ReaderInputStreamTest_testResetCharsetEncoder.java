package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;
import java.nio.charset.CharsetEncoder;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ReaderInputStream.Builder#setCharsetEncoder(CharsetEncoder)} falls back to a
 * default (non-null) encoder when given {@code null}.
 */
public class ReaderInputStreamTest_testResetCharsetEncoder {

    @Test
    void testResetCharsetEncoder() {
        // Passing null as the encoder should reset the builder to a default encoder rather than leave it null.
        final CharsetEncoder encoder = ReaderInputStream.builder()
                .setReader(new StringReader("\uD800"))
                .setCharsetEncoder(null)
                .getCharsetEncoder();

        assertNotNull(encoder, "setCharsetEncoder(null) should fall back to a default, non-null encoder");
    }
}
