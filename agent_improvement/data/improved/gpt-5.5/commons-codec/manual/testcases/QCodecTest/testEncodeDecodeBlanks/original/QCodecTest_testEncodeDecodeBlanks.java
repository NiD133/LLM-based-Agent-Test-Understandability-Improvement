package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class QCodecTest_testEncodeDecodeBlanks {

    static final int[] SWISS_GERMAN_STUFF_UNICODE = { 0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4 };

    static final int[] RUSSIAN_STUFF_UNICODE = { 0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442 };

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
    void testEncodeDecodeBlanks() throws Exception {
        final String plain = "Mind those pesky blanks";
        final String encoded1 = "=?UTF-8?Q?Mind those pesky blanks?=";
        final String encoded2 = "=?UTF-8?Q?Mind_those_pesky_blanks?=";
        final QCodec qcodec = new QCodec();
        qcodec.setEncodeBlanks(false);
        String s = qcodec.encode(plain);
        assertEquals(encoded1, s, "Blanks encoding with the Q codec test");
        qcodec.setEncodeBlanks(true);
        s = qcodec.encode(plain);
        assertEquals(encoded2, s, "Blanks encoding with the Q codec test");
        s = qcodec.decode(encoded1);
        assertEquals(plain, s, "Blanks decoding with the Q codec test");
        s = qcodec.decode(encoded2);
        assertEquals(plain, s, "Blanks decoding with the Q codec test");
    }
}
