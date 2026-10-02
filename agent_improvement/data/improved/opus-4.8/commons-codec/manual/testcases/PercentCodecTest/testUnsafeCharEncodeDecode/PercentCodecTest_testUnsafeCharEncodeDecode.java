package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec} performs a correct round-trip
 * (encode then decode) for a string that mixes "unsafe" characters with
 * a safe space.
 *
 * <p>The default {@link PercentCodec} percent-encodes:</p>
 * <ul>
 *   <li>every non US-ASCII byte (here, the UTF-8 bytes of the Greek letters), and</li>
 *   <li>the literal {@code '%'} escape character.</li>
 * </ul>
 * <p>The plain ASCII space is left untouched because {@code plusForSpace} is
 * {@code false} by default.</p>
 */
public class PercentCodecTest_testUnsafeCharEncodeDecode {

    /** Greek letters (alpha..zeta) followed by a literal percent sign and a space. */
    private static final String PLAIN_TEXT = "αβγδεζ% ";

    /**
     * Expected percent-encoded form:
     * each Greek letter becomes its two UTF-8 bytes (e.g. alpha -> %CE%B1),
     * the '%' becomes %25, and the trailing space stays as-is.
     */
    private static final String EXPECTED_ENCODED =
            "%CE%B1%CE%B2%CE%B3%CE%B4%CE%B5%CE%B6%25 ";

    @Test
    void testUnsafeCharEncodeDecode() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        // Encode the UTF-8 bytes of the plain text.
        final byte[] encodedBytes = percentCodec.encode(PLAIN_TEXT.getBytes(StandardCharsets.UTF_8));
        final String encodedText = new String(encodedBytes, StandardCharsets.UTF_8);
        assertEquals(EXPECTED_ENCODED, encodedText, "Basic PercentCodec unsafe char encoding test");

        // Decoding the encoded bytes must reproduce the original plain text.
        final byte[] decodedBytes = percentCodec.decode(encodedBytes);
        final String decodedText = new String(decodedBytes, StandardCharsets.UTF_8);
        assertEquals(PLAIN_TEXT, decodedText, "Basic PercentCodec unsafe char decoding test");
    }
}
