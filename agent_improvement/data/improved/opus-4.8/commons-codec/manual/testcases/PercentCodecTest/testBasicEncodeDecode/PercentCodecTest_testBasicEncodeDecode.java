package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec} performs a lossless round-trip for a string
 * made up entirely of plain US-ASCII letters.
 *
 * <p>Because none of these characters require Percent-Encoding, both the encoded
 * form and the decoded form are expected to equal the original input.</p>
 */
public class PercentCodecTest_testBasicEncodeDecode {

    @Test
    void testBasicEncodeDecode() throws Exception {
        // Plain ASCII letters: none of these need Percent-Encoding.
        final String originalText = "abcdABCD";
        final PercentCodec percentCodec = new PercentCodec();

        // Encode the original text, then decode the result back.
        final byte[] encodedBytes = percentCodec.encode(originalText.getBytes(StandardCharsets.UTF_8));
        final String encodedText = new String(encodedBytes, StandardCharsets.UTF_8);

        final byte[] decodedBytes = percentCodec.decode(encodedBytes);
        final String decodedText = new String(decodedBytes, StandardCharsets.UTF_8);

        // Safe characters pass through encoding unchanged...
        assertEquals(originalText, encodedText, "Basic PercentCodec encoding test");
        // ...and decoding restores the original input.
        assertEquals(originalText, decodedText, "Basic PercentCodec decoding test");
    }
}
