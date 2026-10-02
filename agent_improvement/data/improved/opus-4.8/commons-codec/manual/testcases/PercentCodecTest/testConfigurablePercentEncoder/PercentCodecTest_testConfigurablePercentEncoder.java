package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec} honors a custom set of "always encode" characters and that
 * encoding followed by decoding returns the original input (a round-trip).
 */
public class PercentCodecTest_testConfigurablePercentEncoder {

    @Test
    void testConfigurablePercentEncoder() throws Exception {
        // The input mixes:
        //  - lower-case letters a, b, c that are configured to always be percent-encoded
        //  - characters 1,2,3,_,-,.,* that must be left untouched
        //  - the Greek letters alpha (U+03B1) and beta (U+03B2), which are non US-ASCII and so are
        //    always percent-encoded as their UTF-8 byte sequences.
        final String input = "abc123_-.*αβ";

        // Configure the codec so that the bytes for 'a', 'b', 'c', 'd', 'e', 'f' are always encoded,
        // and spaces are NOT turned into '+'.
        final byte[] alwaysEncodeChars = "abcdef".getBytes(StandardCharsets.UTF_8);
        final boolean plusForSpace = false;
        final PercentCodec percentCodec = new PercentCodec(alwaysEncodeChars, plusForSpace);

        // Encode the input and read the result back as text for an easy-to-read comparison.
        final byte[] encoded = percentCodec.encode(input.getBytes(StandardCharsets.UTF_8));
        final String encodedText = new String(encoded, StandardCharsets.UTF_8);

        // a/b/c -> %61/%62/%63, the safe characters pass through, alpha -> %CE%B1, beta -> %CE%B2.
        final String expectedEncoded = "%61%62%63123_-.*%CE%B1%CE%B2";
        assertEquals(expectedEncoded, encodedText, "Configurable PercentCodec encoding test");

        // Decoding the encoded bytes must reproduce the original input exactly.
        final byte[] decoded = percentCodec.decode(encoded);
        final String decodedText = new String(decoded, StandardCharsets.UTF_8);
        assertEquals(decodedText, input, "Configurable PercentCodec decoding test");
    }
}
