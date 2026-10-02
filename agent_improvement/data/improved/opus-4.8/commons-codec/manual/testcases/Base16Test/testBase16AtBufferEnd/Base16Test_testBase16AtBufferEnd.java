package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16} encodes the requested slice of a buffer even when
 * that slice sits at the very end of the buffer (i.e. there are leading padding
 * bytes but no trailing padding bytes).
 */
public class Base16Test_testBase16AtBufferEnd {

    /** Plain-text input that gets encoded. */
    private static final String CONTENT = "Hello World";

    /** Upper-case Base16 (hex) encoding of {@link #CONTENT} in UTF-8. */
    private static final String EXPECTED_HEX = "48656C6C6F20576F726C64";

    /**
     * Encodes {@link #CONTENT} after placing it inside a larger buffer, surrounded
     * by {@code leadingPadSize} zero bytes before it and {@code trailingPadSize}
     * zero bytes after it. Only the content region is passed to the encoder, so the
     * surrounding padding must not affect the result.
     */
    private void assertContentEncodesRegardlessOfPadding(final int leadingPadSize, final int trailingPadSize) {
        final byte[] contentBytes = StringUtils.getBytesUtf8(CONTENT);

        // Build buffer layout: [leadingPad][content][trailingPad]
        byte[] buffer = ArrayUtils.addAll(contentBytes, new byte[trailingPadSize]);
        buffer = ArrayUtils.addAll(new byte[leadingPadSize], buffer);

        // Encode only the content region, which starts right after the leading pad.
        final byte[] encodedBytes = new Base16().encode(buffer, leadingPadSize, contentBytes.length);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);

        assertEquals(EXPECTED_HEX, encodedContent, "encoding hello world");
    }

    @Test
    void testBase16AtBufferEnd() {
        // Content sits at the end of the buffer: padding before it, none after it.
        assertContentEncodesRegardlessOfPadding(100, 0);
    }
}
