package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeInvalidContent {

    private static final int[] SWISS_GERMAN_CODE_POINTS = {
            0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    private String stringFromCodePoints(final int[] codePoints) {
        final StringBuilder text = new StringBuilder();
        if (codePoints != null) {
            for (final int codePoint : codePoints) {
                text.append((char) codePoint);
            }
        }
        return text.toString();
    }

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }

    @Test
    void testDecodeInvalidContent() throws DecoderException {
        final String swissGermanText = stringFromCodePoints(SWISS_GERMAN_CODE_POINTS);
        final URLCodec urlCodec = new URLCodec();
        final byte[] encodedBytesWithoutEscapes = swissGermanText.getBytes(StandardCharsets.ISO_8859_1);

        final byte[] decodedBytes = urlCodec.decode(encodedBytesWithoutEscapes);

        assertEquals(encodedBytesWithoutEscapes.length, decodedBytes.length);
        for (int i = 0; i < encodedBytesWithoutEscapes.length; i++) {
            assertEquals(encodedBytesWithoutEscapes[i], decodedBytes[i]);
        }
        validateState(urlCodec);
    }
}
