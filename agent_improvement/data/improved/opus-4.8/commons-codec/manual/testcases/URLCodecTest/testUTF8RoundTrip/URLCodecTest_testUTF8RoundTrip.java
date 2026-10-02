package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec} can encode non-ASCII text to its
 * www-form-urlencoded form using UTF-8 and decode it back to the original
 * string (a lossless "round trip").
 */
public class URLCodecTest_testUTF8RoundTrip {

    /** The Russian phrase "Всем_привет" expressed as Unicode code points. */
    private static final int[] RUSSIAN_TEXT_CODE_POINTS = {
        0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442
    };

    /** The Swiss-German phrase "Grüezi_zämä" expressed as Unicode code points. */
    private static final int[] SWISS_GERMAN_TEXT_CODE_POINTS = {
        0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    /** Expected URL-encoded (UTF-8) form of the Russian phrase. */
    private static final String RUSSIAN_TEXT_ENCODED =
        "%D0%92%D1%81%D0%B5%D0%BC_%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82";

    /** Expected URL-encoded (UTF-8) form of the Swiss-German phrase. */
    private static final String SWISS_GERMAN_TEXT_ENCODED = "Gr%C3%BCezi_z%C3%A4m%C3%A4";

    /** Builds a String from an array of Unicode code points. */
    private static String toString(final int[] codePoints) {
        final StringBuilder buffer = new StringBuilder();
        for (final int codePoint : codePoints) {
            buffer.append((char) codePoint);
        }
        return buffer.toString();
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianText = toString(RUSSIAN_TEXT_CODE_POINTS);
        final String swissGermanText = toString(SWISS_GERMAN_TEXT_CODE_POINTS);
        final URLCodec urlCodec = new URLCodec();

        // Encoding produces the expected percent-encoded UTF-8 form.
        assertEquals(RUSSIAN_TEXT_ENCODED, urlCodec.encode(russianText, CharEncoding.UTF_8));
        assertEquals(SWISS_GERMAN_TEXT_ENCODED, urlCodec.encode(swissGermanText, CharEncoding.UTF_8));

        // Decoding the encoded form restores the original text (round trip).
        assertEquals(russianText,
            urlCodec.decode(urlCodec.encode(russianText, CharEncoding.UTF_8), CharEncoding.UTF_8));
        assertEquals(swissGermanText,
            urlCodec.decode(urlCodec.encode(swissGermanText, CharEncoding.UTF_8), CharEncoding.UTF_8));
    }
}
