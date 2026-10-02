package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;
import java.nio.charset.CharsetEncoder;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testResetCharsetEncoder {

    /**
     * Passing null to setCharsetEncoder should reset to a default (non-null) encoder
     * rather than leaving the builder in an invalid state.
     */
    @Test
    void testResetCharsetEncoder() {
        CharsetEncoder encoder = ReaderInputStream.builder()
                .setReader(new StringReader("\uD800"))
                .setCharsetEncoder(null)
                .getCharsetEncoder();

        assertNotNull(encoder);
    }
}
