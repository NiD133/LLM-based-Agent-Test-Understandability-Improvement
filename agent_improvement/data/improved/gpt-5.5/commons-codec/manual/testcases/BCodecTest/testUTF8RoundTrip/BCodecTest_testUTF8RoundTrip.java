package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.Test;

public class BCodecTest_testUTF8RoundTrip {

    private static final int[] SWISS_GERMAN_UNICODE = {
            0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    private static final int[] RUSSIAN_UNICODE = {
            0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442
    };

    private String stringFromCodePoints(final int[] unicodeChars) {
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
        final String russianMessage = stringFromCodePoints(RUSSIAN_UNICODE);
        final String swissGermanMessage = stringFromCodePoints(SWISS_GERMAN_UNICODE);
        final BCodec bcodec = new BCodec(CharEncoding.UTF_8);

        assertEquals("=?UTF-8?B?0JLRgdC10Lxf0L/RgNC40LLQtdGC?=", bcodec.encode(russianMessage));
        assertEquals("=?UTF-8?B?R3LDvGV6aV96w6Rtw6Q=?=", bcodec.encode(swissGermanMessage));
        assertEquals(russianMessage, bcodec.decode(bcodec.encode(russianMessage)));
        assertEquals(swissGermanMessage, bcodec.decode(bcodec.encode(swissGermanMessage)));
    }
}
