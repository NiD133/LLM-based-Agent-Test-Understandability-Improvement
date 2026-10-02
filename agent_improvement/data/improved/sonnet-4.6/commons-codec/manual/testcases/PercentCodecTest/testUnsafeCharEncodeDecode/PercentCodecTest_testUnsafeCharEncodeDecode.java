package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testUnsafeCharEncodeDecode {

    // Greek lowercase letters αβγδεζ, followed by '%' (always encoded) and a literal space
    private static final String INPUT = "αβγδεζ% ";

    // Each Greek letter encodes to two UTF-8 bytes (CE Bx), each percent-encoded;
    // '%' becomes '%25'; space is left as a literal space by the default PercentCodec.
    private static final String EXPECTED_ENCODED = "%CE%B1%CE%B2%CE%B3%CE%B4%CE%B5%CE%B6%25 ";

    @Test
    void testUnsafeCharEncodeDecode() throws Exception {
        // Arrange: default PercentCodec encodes non-ASCII chars and '%'; space is left unencoded
        final PercentCodec percentCodec = new PercentCodec();
        final byte[] inputBytes = INPUT.getBytes(StandardCharsets.UTF_8);

        // Act: encode the raw UTF-8 bytes, then round-trip back through decode
        final byte[] encodedBytes = percentCodec.encode(inputBytes);
        final byte[] decodedBytes = percentCodec.decode(encodedBytes);

        // Assert: non-ASCII and '%' are percent-encoded; space is preserved literally
        final String encodedString = new String(encodedBytes, StandardCharsets.UTF_8);
        assertEquals(EXPECTED_ENCODED, encodedString, "Basic PercentCodec unsafe char encoding test");

        // Assert: decoding the encoded bytes restores the original string exactly
        final String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        assertEquals(INPUT, decodedString, "Basic PercentCodec unsafe char decoding test");
    }
}
