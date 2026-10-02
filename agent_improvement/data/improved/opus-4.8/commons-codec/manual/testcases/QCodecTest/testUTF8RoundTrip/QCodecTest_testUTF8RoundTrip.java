package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link QCodec}, when configured with the UTF-8 charset, both:
 * <ul>
 *   <li>produces the exact RFC 1522 "Q" encoded word for non-ASCII text, and</li>
 *   <li>can decode that encoded word back into the original text (a round trip).</li>
 * </ul>
 */
public class QCodecTest_testUTF8RoundTrip {

    /**
     * Unicode code points for the Swiss-German text "Gr&uuml;ezi_z&auml;m&auml;".
     * Contains the accented characters &uuml; (0xFC) and &auml; (0xE4) plus an underscore.
     */
    private static final int[] SWISS_GERMAN_TEXT_CODE_POINTS = {
        0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4
    };

    /**
     * Unicode code points for the Russian text "&#x412;&#x441;&#x435;&#x43C;_&#x43F;&#x440;&#x438;&#x432;&#x435;&#x442;"
     * ("Vsem_privet"). Contains Cyrillic characters plus an underscore.
     */
    private static final int[] RUSSIAN_TEXT_CODE_POINTS = {
        0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442
    };

    /** Builds a String from an array of Unicode code points. */
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
        final String russianText = toString(RUSSIAN_TEXT_CODE_POINTS);
        final String swissGermanText = toString(SWISS_GERMAN_TEXT_CODE_POINTS);
        final QCodec qcodec = new QCodec(CharEncoding.UTF_8);

        // Encoding must yield the exact RFC 1522 "Q" encoded word for each input.
        assertEquals(
            "=?UTF-8?Q?=D0=92=D1=81=D0=B5=D0=BC=5F=D0=BF=D1=80=D0=B8=D0=B2=D0=B5=D1=82?=",
            qcodec.encode(russianText));
        assertEquals(
            "=?UTF-8?Q?Gr=C3=BCezi=5Fz=C3=A4m=C3=A4?=",
            qcodec.encode(swissGermanText));

        // Decoding the freshly encoded text must reproduce the original input.
        assertEquals(russianText, qcodec.decode(qcodec.encode(russianText)));
        assertEquals(swissGermanText, qcodec.decode(qcodec.encode(swissGermanText)));
    }
}
