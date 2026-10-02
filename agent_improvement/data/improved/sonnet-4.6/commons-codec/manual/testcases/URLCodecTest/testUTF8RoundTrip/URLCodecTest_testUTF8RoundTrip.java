package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.Test;

/**
 * Tests that URLCodec correctly encodes non-ASCII text to its UTF-8 percent-encoded form
 * and that decoding the encoded result restores the original string (round-trip integrity).
 */
public class URLCodecTest_testUTF8RoundTrip {

    // Unicode code points for "Grüezi_zämä" (Swiss-German greeting)
    static final int[] SWISS_GERMAN_STUFF_UNICODE = {
        0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    // Unicode code points for "Всем_привет" (Russian greeting meaning "Hello everyone")
    static final int[] RUSSIAN_STUFF_UNICODE = {
        0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442
    };

    // Expected UTF-8 percent-encoded output for the Russian greeting
    private static final String RUSSIAN_ENCODED =
        "%D0%92%D1%81%D0%B5%D0%BC_%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82";

    // Expected UTF-8 percent-encoded output for the Swiss-German greeting
    private static final String SWISS_GERMAN_ENCODED = "Gr%C3%BCezi_z%C3%A4m%C3%A4";

    private String constructString(final int[] unicodeChars) {
        final StringBuilder buffer = new StringBuilder();
        if (unicodeChars != null) {
            for (final int unicodeChar : unicodeChars) {
                buffer.append((char) unicodeChar);
            }
        }
        return buffer.toString();
    }

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianText = constructString(RUSSIAN_STUFF_UNICODE);
        final String swissGermanText = constructString(SWISS_GERMAN_STUFF_UNICODE);
        final URLCodec urlCodec = new URLCodec();
        validateState(urlCodec);

        // Encoding non-ASCII characters with UTF-8 should produce correct percent-encoded strings
        assertEquals(RUSSIAN_ENCODED, urlCodec.encode(russianText, CharEncoding.UTF_8));
        assertEquals(SWISS_GERMAN_ENCODED, urlCodec.encode(swissGermanText, CharEncoding.UTF_8));

        // Decoding the encoded output must restore the original text (round-trip)
        assertEquals(russianText, urlCodec.decode(urlCodec.encode(russianText, CharEncoding.UTF_8), CharEncoding.UTF_8));
        assertEquals(swissGermanText, urlCodec.decode(urlCodec.encode(swissGermanText, CharEncoding.UTF_8), CharEncoding.UTF_8));

        validateState(urlCodec);
    }
}
