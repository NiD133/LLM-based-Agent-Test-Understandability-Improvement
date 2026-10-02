package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.Test;

public class QCodecTest_testUTF8RoundTrip {

    private static final int[] SWISS_GERMAN_CODE_POINTS = {
        0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    private static final int[] RUSSIAN_CODE_POINTS = {
        0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442
    };

    private static final String EXPECTED_RUSSIAN_UTF8_Q_ENCODING =
        "=?UTF-8?Q?=D0=92=D1=81=D0=B5=D0=BC=5F=D0=BF=D1=80=D0=B8=D0=B2=D0=B5=D1=82?=";

    private static final String EXPECTED_SWISS_GERMAN_UTF8_Q_ENCODING =
        "=?UTF-8?Q?Gr=C3=BCezi=5Fz=C3=A4m=C3=A4?=";

    private String stringFromCodePoints(final int[] codePoints) {
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
        final String russianMessage = stringFromCodePoints(RUSSIAN_CODE_POINTS);
        final String swissGermanMessage = stringFromCodePoints(SWISS_GERMAN_CODE_POINTS);
        final QCodec qcodec = new QCodec(CharEncoding.UTF_8);

        assertEquals(EXPECTED_RUSSIAN_UTF8_Q_ENCODING, qcodec.encode(russianMessage));
        assertEquals(EXPECTED_SWISS_GERMAN_UTF8_Q_ENCODING, qcodec.encode(swissGermanMessage));
        assertEquals(russianMessage, qcodec.decode(qcodec.encode(russianMessage)));
        assertEquals(swissGermanMessage, qcodec.decode(qcodec.encode(swissGermanMessage)));
    }
}
