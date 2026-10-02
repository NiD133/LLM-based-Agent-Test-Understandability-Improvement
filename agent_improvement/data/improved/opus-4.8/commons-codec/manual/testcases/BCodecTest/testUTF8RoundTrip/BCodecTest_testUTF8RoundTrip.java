package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BCodec}, configured with the UTF-8 charset, encodes
 * non-ASCII text into the expected RFC 1522 "B" (Base64) encoded-word form and
 * can decode that form back to the original text (a round trip).
 */
public class BCodecTest_testUTF8RoundTrip {

    /**
     * Unicode code points for the Russian phrase used as a test message.
     * Includes Cyrillic characters that must be encoded as multi-byte UTF-8.
     */
    private static final int[] RUSSIAN_MESSAGE_CODE_POINTS = {
        0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442
    };

    /**
     * Unicode code points for the Swiss-German phrase used as a test message.
     * Includes umlaut characters that must be encoded as multi-byte UTF-8.
     */
    private static final int[] SWISS_GERMAN_MESSAGE_CODE_POINTS = {
        0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    /** Expected RFC 1522 "B" encoded-word for the Russian message under UTF-8. */
    private static final String RUSSIAN_MESSAGE_ENCODED = "=?UTF-8?B?0JLRgdC10Lxf0L/RgNC40LLQtdGC?=";

    /** Expected RFC 1522 "B" encoded-word for the Swiss-German message under UTF-8. */
    private static final String SWISS_GERMAN_MESSAGE_ENCODED = "=?UTF-8?B?R3LDvGV6aV96w6Rtw6Q=?=";

    /**
     * Builds a String from an array of Unicode code points.
     *
     * @param codePoints the code points to assemble; treated as empty when {@code null}.
     * @return the assembled String.
     */
    private String toString(final int[] codePoints) {
        final StringBuilder buffer = new StringBuilder();
        if (codePoints != null) {
            for (final int codePoint : codePoints) {
                buffer.append((char) codePoint);
            }
        }
        return buffer.toString();
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianMessage = toString(RUSSIAN_MESSAGE_CODE_POINTS);
        final String swissGermanMessage = toString(SWISS_GERMAN_MESSAGE_CODE_POINTS);
        final BCodec bcodec = new BCodec(CharEncoding.UTF_8);

        // Encoding produces the expected RFC 1522 "B" encoded-words.
        assertEquals(RUSSIAN_MESSAGE_ENCODED, bcodec.encode(russianMessage));
        assertEquals(SWISS_GERMAN_MESSAGE_ENCODED, bcodec.encode(swissGermanMessage));

        // Decoding an encoded message restores the original text (round trip).
        assertEquals(russianMessage, bcodec.decode(bcodec.encode(russianMessage)));
        assertEquals(swissGermanMessage, bcodec.decode(bcodec.encode(swissGermanMessage)));
    }
}
