package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58} can round-trip a known text payload: encoding the
 * bytes to a Base58 string and then decoding that string back to the original bytes.
 */
public class Base58Test_testHexEncoding {

    @Test
    void testHexEncoding() {
        // "Hello World!" expressed as its hex-character string (this is the literal
        // text that gets encoded, not raw hex bytes).
        final String originalText = "48656c6c6f20576f726c6421";
        final String expectedBase58 = "5m7UdtXCfQxGvX2K9dLrkNs7AFMS98qn8";

        final byte[] encodedBytes = new Base58().encode(StringUtils.getBytesUtf8(originalText));
        final String actualBase58 = StringUtils.newStringUtf8(encodedBytes);

        final byte[] decodedBytes = new Base58().decode(actualBase58);
        final String roundTrippedText = StringUtils.newStringUtf8(decodedBytes);

        assertEquals(expectedBase58, actualBase58, "Hex encoding failed");
        assertEquals(originalText, roundTrippedText, "Hex decoding failed");
    }
}
