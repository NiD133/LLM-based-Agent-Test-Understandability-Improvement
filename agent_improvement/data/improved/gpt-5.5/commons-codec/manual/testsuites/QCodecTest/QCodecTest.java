/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * Quoted-printable codec test cases.
 */
class QCodecTest {

    private static final String DEFAULT_CHARSET_Q_PREFIX = "=?UTF-8?Q?";
    private static final String RFC_1522_SUFFIX = "?=";

    private static final int[] SWISS_GERMAN_STUFF_UNICODE = { 0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4 };
    private static final int[] RUSSIAN_STUFF_UNICODE = { 0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442 };

    private String constructString(final int[] unicodeChars) {
        final StringBuilder buffer = new StringBuilder();
        if (unicodeChars != null) {
            for (final int unicodeChar : unicodeChars) {
                buffer.append((char) unicodeChar);
            }
        }
        return buffer.toString();
    }

    private String qEncodedUtf8(final String encodedText) {
        return DEFAULT_CHARSET_Q_PREFIX + encodedText + RFC_1522_SUFFIX;
    }

    private void assertEncodesAndDecodes(final QCodec qcodec, final String plain, final String encoded, final String assertionMessage)
            throws DecoderException, EncoderException {
        assertEquals(encoded, qcodec.encode(plain), assertionMessage + " encoding test");
        assertEquals(plain, qcodec.decode(encoded), assertionMessage + " decoding test");
    }

    @Test
    void testBasicEncodeDecode() throws Exception {
        final QCodec qcodec = new QCodec();
        final String plain = "= Hello there =\r\n";
        final String encoded = qEncodedUtf8("=3D Hello there =3D=0D=0A");

        assertEncodesAndDecodes(qcodec, plain, encoded, "Basic Q");
    }

    @Test
    void testDecodeObjects() throws Exception {
        final QCodec qcodec = new QCodec();
        final String decoded = qEncodedUtf8("1+1 =3D 2");

        final String plain = (String) qcodec.decode((Object) decoded);
        assertEquals("1+1 = 2", plain, "Basic Q decoding test");

        final Object result = qcodec.decode((Object) null);
        assertNull(result, "Decoding a null Object should return null");
        assertThrows(DecoderException.class, () -> qcodec.decode(Double.valueOf(3.0d)), "Trying to url encode a Double object should cause an exception.");
    }

    @Test
    void testDecodeStringWithNull() throws Exception {
        final QCodec qcodec = new QCodec();
        final String test = null;

        final String result = qcodec.decode(test);

        assertNull(result, "Result should be null");
    }

    @Test
    void testEncodeDecodeBlanks() throws Exception {
        final String plain = "Mind those pesky blanks";
        final String encodedWithSpaces = qEncodedUtf8("Mind those pesky blanks");
        final String encodedWithUnderscores = qEncodedUtf8("Mind_those_pesky_blanks");
        final QCodec qcodec = new QCodec();

        qcodec.setEncodeBlanks(false);
        String result = qcodec.encode(plain);
        assertEquals(encodedWithSpaces, result, "Blanks encoding with the Q codec test");

        qcodec.setEncodeBlanks(true);
        result = qcodec.encode(plain);
        assertEquals(encodedWithUnderscores, result, "Blanks encoding with the Q codec test");

        result = qcodec.decode(encodedWithSpaces);
        assertEquals(plain, result, "Blanks decoding with the Q codec test");

        result = qcodec.decode(encodedWithUnderscores);
        assertEquals(plain, result, "Blanks decoding with the Q codec test");
    }

    @Test
    void testEncodeDecodeNull() throws Exception {
        final QCodec qcodec = new QCodec();

        assertNull(qcodec.encode((String) null), "Null string Q encoding test");
        assertNull(qcodec.decode((String) null), "Null string Q decoding test");
    }

    @Test
    void testEncodeObjects() throws Exception {
        final QCodec qcodec = new QCodec();
        final String plain = "1+1 = 2";
        final String expectedEncoded = qEncodedUtf8("1+1 =3D 2");

        final String encoded = (String) qcodec.encode((Object) plain);
        assertEquals(expectedEncoded, encoded, "Basic Q encoding test");

        final Object result = qcodec.encode((Object) null);
        assertNull(result, "Encoding a null Object should return null");
        assertThrows(EncoderException.class, () -> qcodec.encode(Double.valueOf(3.0d)), "Trying to url encode a Double object should cause an exception.");
    }

    @Test
    void testEncodeStringWithNull() throws Exception {
        final QCodec qcodec = new QCodec();
        final String test = null;

        final String result = qcodec.encode(test, "charset");

        assertNull(result, "Result should be null");
    }

    @Test
    void testInvalidEncoding() {
        assertThrows(UnsupportedCharsetException.class, () -> new QCodec("NONSENSE"));
    }

    @Test
    void testLetUsMakeCloverHappy() throws Exception {
        final QCodec qcodec = new QCodec();

        qcodec.setEncodeBlanks(true);
        assertTrue(qcodec.isEncodeBlanks());

        qcodec.setEncodeBlanks(false);
        assertFalse(qcodec.isEncodeBlanks());
    }

    @Test
    void testNullInput() throws Exception {
        final QCodec qcodec = new QCodec();

        assertNull(qcodec.doDecoding(null));
        assertNull(qcodec.doEncoding(null));
    }

    @Test
    void testUnsafeEncodeDecode() throws Exception {
        final QCodec qcodec = new QCodec();
        final String plain = "?_=\r\n";
        final String encoded = qEncodedUtf8("=3F=5F=3D=0D=0A");

        assertEncodesAndDecodes(qcodec, plain, encoded, "Unsafe chars Q");
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianText = constructString(RUSSIAN_STUFF_UNICODE);
        final String swissGermanText = constructString(SWISS_GERMAN_STUFF_UNICODE);
        final QCodec qcodec = new QCodec(CharEncoding.UTF_8);

        assertEquals(qEncodedUtf8("=D0=92=D1=81=D0=B5=D0=BC=5F=D0=BF=D1=80=D0=B8=D0=B2=D0=B5=D1=82"), qcodec.encode(russianText));
        assertEquals(qEncodedUtf8("Gr=C3=BCezi=5Fz=C3=A4m=C3=A4"), qcodec.encode(swissGermanText));

        assertEquals(russianText, qcodec.decode(qcodec.encode(russianText)));
        assertEquals(swissGermanText, qcodec.decode(qcodec.encode(swissGermanText)));
    }

}
