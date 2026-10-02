package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.Test;

public class QCodecTest_testUTF8RoundTrip {

    // Unicode code points for "Grüezi_zämä" (Swiss German greeting with umlauts)
    static final int[] SWISS_GERMAN_STUFF_UNICODE = {
        0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    // Unicode code points for "Всем_привет" (Russian "Hello everyone")
    static final int[] RUSSIAN_STUFF_UNICODE = {
        0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442
    };

    /**
     * Builds a String from an array of Unicode code point values.
     */
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
     * Verifies that QCodec correctly encodes non-ASCII text to RFC 1522 Q-encoded form
     * and that decoding the encoded result restores the original string (round-trip).
     *
     * Two languages are tested to cover different multi-byte UTF-8 sequences:
     *   - Russian (Cyrillic, 2-byte UTF-8 per character)
     *   - Swiss German (Latin with umlauts, mixed 1- and 2-byte UTF-8)
     */
    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianText      = constructString(RUSSIAN_STUFF_UNICODE);
        final String swissGermanText  = constructString(SWISS_GERMAN_STUFF_UNICODE);

        final QCodec qcodec = new QCodec(CharEncoding.UTF_8);

        // Verify the encoded form matches the expected RFC 1522 Q-encoded header value
        final String expectedEncodedRussian     = "=?UTF-8?Q?=D0=92=D1=81=D0=B5=D0=BC=5F=D0=BF=D1=80=D0=B8=D0=B2=D0=B5=D1=82?=";
        final String expectedEncodedSwissGerman = "=?UTF-8?Q?Gr=C3=BCezi=5Fz=C3=A4m=C3=A4?=";

        assertEquals(expectedEncodedRussian,     qcodec.encode(russianText));
        assertEquals(expectedEncodedSwissGerman, qcodec.encode(swissGermanText));

        // Verify round-trip: decode(encode(original)) == original
        assertEquals(russianText,     qcodec.decode(qcodec.encode(russianText)));
        assertEquals(swissGermanText, qcodec.decode(qcodec.encode(swissGermanText)));
    }
}
