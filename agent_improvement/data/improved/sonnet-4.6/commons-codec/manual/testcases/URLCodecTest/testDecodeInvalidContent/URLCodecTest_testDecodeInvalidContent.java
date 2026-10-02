package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeInvalidContent {

    // "Grüezi_zämä" — Swiss German text whose ISO-8859-1 bytes are not URL-encoded
    static final int[] SWISS_GERMAN_STUFF_UNICODE = { 0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4 };

    private String constructString(final int[] unicodeChars) {
        final StringBuilder buffer = new StringBuilder();
        if (unicodeChars != null) {
            for (final int unicodeChar : unicodeChars) {
                buffer.append((char) unicodeChar);
            }
        }
        return buffer.toString();
    }

    /**
     * Verifies that decoding raw (non-URL-encoded) bytes returns them unchanged.
     * Bytes that are not '%'-escaped or '+' pass through URLCodec.decode as-is.
     */
    @Test
    void testDecodeInvalidContent() throws DecoderException {
        final String swissGermanText = constructString(SWISS_GERMAN_STUFF_UNICODE);
        final URLCodec urlCodec = new URLCodec();

        final byte[] rawBytes = swissGermanText.getBytes(StandardCharsets.ISO_8859_1);
        final byte[] decodedBytes = urlCodec.decode(rawBytes);

        assertEquals(rawBytes.length, decodedBytes.length);
        assertArrayEquals(rawBytes, decodedBytes);
    }
}
