package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.Test;

public class BCodecTest_testUTF8RoundTrip {

    // "Grüezi_zämä" — Swiss German greeting, exercises non-ASCII Latin characters (ü, ä)
    static final int[] SWISS_GERMAN_STUFF_UNICODE = {
        0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    // "Всем_привет" — Russian greeting, exercises multi-byte Cyrillic characters
    static final int[] RUSSIAN_STUFF_UNICODE = {
        0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442
    };

    private String constructString(final int[] unicodeChars) {
        final StringBuilder buffer = new StringBuilder();
        if (unicodeChars != null) {
            for (final int unicodeChar : unicodeChars) {
                buffer.append((char) unicodeChar);
            }
        }
        return buffer.toString();
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianText    = constructString(RUSSIAN_STUFF_UNICODE);
        final String swissGermanText = constructString(SWISS_GERMAN_STUFF_UNICODE);

        final BCodec bcodec = new BCodec(CharEncoding.UTF_8);

        // Verify that each string encodes to the expected RFC 1522 encoded-word representation
        assertEquals("=?UTF-8?B?0JLRgdC10Lxf0L/RgNC40LLQtdGC?=", bcodec.encode(russianText));
        assertEquals("=?UTF-8?B?R3LDvGV6aV96w6Rtw6Q=?=",           bcodec.encode(swissGermanText));

        // Verify that encoding then decoding restores the original string (round-trip)
        assertEquals(russianText,    bcodec.decode(bcodec.encode(russianText)));
        assertEquals(swissGermanText, bcodec.decode(bcodec.encode(swissGermanText)));
    }
}
