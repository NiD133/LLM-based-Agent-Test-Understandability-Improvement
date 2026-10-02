package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58} can encode a UTF-8 string and decode it back to
 * the original value (a round-trip test).
 */
public class Base58Test_testBase58 {

    /** Plain-text input that gets encoded and then decoded back. */
    private static final String PLAIN_TEXT = "Hello World";

    /** Known Base58 encoding of {@link #PLAIN_TEXT}. */
    private static final String EXPECTED_BASE58 = "JxF12TrwUP45BMd";

    @Test
    void testBase58() {
        // Encode the plain text and confirm it matches the known Base58 value.
        final byte[] encodedBytes = new Base58().encode(StringUtils.getBytesUtf8(PLAIN_TEXT));
        final String actualBase58 = StringUtils.newStringUtf8(encodedBytes);
        assertEquals(EXPECTED_BASE58, actualBase58, "encoding hello world");

        // Decode the Base58 bytes and confirm we recover the original plain text.
        final byte[] decodedBytes = new Base58().decode(encodedBytes);
        final String roundTrippedText = StringUtils.newStringUtf8(decodedBytes);
        assertEquals(PLAIN_TEXT, roundTrippedText, "decoding hello world");
    }
}
