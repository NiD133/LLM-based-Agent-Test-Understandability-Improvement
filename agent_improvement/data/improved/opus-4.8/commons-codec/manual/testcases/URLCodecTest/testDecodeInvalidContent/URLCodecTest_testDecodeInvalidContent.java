package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link URLCodec#decode(byte[])} handles "invalid" content: bytes
 * that are NOT URL-encoded (i.e. contain no '%' escape sequences and no '+').
 * In that case decoding must be a no-op and return the bytes unchanged.
 */
public class URLCodecTest_testDecodeInvalidContent {

    /** Unicode code points for the Swiss-German phrase "Gr&uuml;ezi_z&auml;m&auml;". */
    private static final int[] SWISS_GERMAN_STUFF_UNICODE = {
        0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    private static String toStringFromCodePoints(final int[] unicodeChars) {
        final StringBuilder buffer = new StringBuilder();
        for (final int unicodeChar : unicodeChars) {
            buffer.append((char) unicodeChar);
        }
        return buffer.toString();
    }

    @Test
    void decodeLeavesNonUrlEncodedBytesUnchanged() throws DecoderException {
        final URLCodec urlCodec = new URLCodec();

        // ISO-8859-1 bytes of the Swiss-German text contain no URL escape characters,
        // so they are not valid URL-encoded input and should pass through untouched.
        final String swissGermanText = toStringFromCodePoints(SWISS_GERMAN_STUFF_UNICODE);
        final byte[] input = swissGermanText.getBytes(StandardCharsets.ISO_8859_1);

        final byte[] output = urlCodec.decode(input);

        assertArrayEquals(input, output);
    }
}
