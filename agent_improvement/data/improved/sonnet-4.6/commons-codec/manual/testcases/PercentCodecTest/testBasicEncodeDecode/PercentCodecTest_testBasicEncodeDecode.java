package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testBasicEncodeDecode {

    /**
     * Verifies that pure ASCII alphanumeric input is preserved verbatim through a
     * round-trip of encode → decode.  The default PercentCodec only encodes the
     * '%' escape character and non-ASCII bytes, so "abcdABCD" must come out of
     * encode() unchanged, and decode() must reproduce the original string.
     */
    @Test
    void testBasicEncodeDecode() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        // Input contains only ASCII letters — no percent-encoding should occur.
        final String input = "abcdABCD";
        final byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

        // Encode: ASCII alphanumeric characters must pass through unmodified.
        final byte[] encodedBytes = percentCodec.encode(inputBytes);
        final String encodedString = new String(encodedBytes, StandardCharsets.UTF_8);
        assertEquals(input, encodedString, "Basic PercentCodec encoding test");

        // Decode: round-trip must reconstruct the original string exactly.
        final byte[] decodedBytes = percentCodec.decode(encodedBytes);
        final String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        assertEquals(input, decodedString, "Basic PercentCodec decoding test");
    }
}
