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
 * Tests for {@link QCodec}, the "Q" encoding variant used in MIME message
 * headers (RFC 1522). Each test exercises a round trip (encode then decode) or
 * a specific edge case such as {@code null} handling and unsupported inputs.
 */
class QCodecTest {

    /** Unicode code points for the Swiss-German phrase "Gr&uuml;ezi_z&auml;m&auml;". */
    private static final int[] SWISS_GERMAN_STUFF_UNICODE =
            { 0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4 };

    /** Unicode code points for the Russian phrase "&#x412;&#x441;&#x435;&#x43C;_&#x43F;&#x440;&#x438;&#x432;&#x435;&#x442;". */
    private static final int[] RUSSIAN_STUFF_UNICODE =
            { 0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442 };

    /**
     * Builds a String from an array of Unicode code points so that non-ASCII
     * test data can be expressed portably as numeric literals.
     *
     * @param unicodeChars the code points, or {@code null} for an empty result
     * @return the assembled String
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

    @Test
    void testBasicEncodeDecode() throws Exception {
        final QCodec qcodec = new QCodec();
        final String plain = "= Hello there =\r\n";

        final String encoded = qcodec.encode(plain);
        assertEquals("=?UTF-8?Q?=3D Hello there =3D=0D=0A?=", encoded, "Basic Q encoding test");
        assertEquals(plain, qcodec.decode(encoded), "Basic Q decoding test");
    }

    @Test
    void testDecodeObjects() throws Exception {
        final QCodec qcodec = new QCodec();

        // A String wrapped as Object is decoded normally.
        final String encoded = "=?UTF-8?Q?1+1 =3D 2?=";
        final String plain = (String) qcodec.decode((Object) encoded);
        assertEquals("1+1 = 2", plain, "Basic Q decoding test");

        // A null Object decodes to null.
        final Object result = qcodec.decode((Object) null);
        assertNull(result, "Decoding a null Object should return null");

        // A non-String Object cannot be decoded.
        assertThrows(DecoderException.class, () -> qcodec.decode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }

    @Test
    void testDecodeStringWithNull() throws Exception {
        final QCodec qcodec = new QCodec();

        final String result = qcodec.decode((String) null);
        assertNull(result, "Result should be null");
    }

    @Test
    void testEncodeDecodeBlanks() throws Exception {
        final QCodec qcodec = new QCodec();
        final String plain = "Mind those pesky blanks";
        final String encodedWithSpaces = "=?UTF-8?Q?Mind those pesky blanks?=";
        final String encodedWithUnderscores = "=?UTF-8?Q?Mind_those_pesky_blanks?=";

        // With blank encoding off, spaces are preserved as spaces.
        qcodec.setEncodeBlanks(false);
        assertEquals(encodedWithSpaces, qcodec.encode(plain), "Blanks encoding with the Q codec test");

        // With blank encoding on, spaces are encoded as underscores.
        qcodec.setEncodeBlanks(true);
        assertEquals(encodedWithUnderscores, qcodec.encode(plain), "Blanks encoding with the Q codec test");

        // Both encodings decode back to the original text.
        assertEquals(plain, qcodec.decode(encodedWithSpaces), "Blanks decoding with the Q codec test");
        assertEquals(plain, qcodec.decode(encodedWithUnderscores), "Blanks decoding with the Q codec test");
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

        // A String wrapped as Object is encoded normally.
        final String plain = "1+1 = 2";
        final String encoded = (String) qcodec.encode((Object) plain);
        assertEquals("=?UTF-8?Q?1+1 =3D 2?=", encoded, "Basic Q encoding test");

        // A null Object encodes to null.
        final Object result = qcodec.encode((Object) null);
        assertNull(result, "Encoding a null Object should return null");

        // A non-String Object cannot be encoded.
        assertThrows(EncoderException.class, () -> qcodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }

    @Test
    void testEncodeStringWithNull() throws Exception {
        final QCodec qcodec = new QCodec();

        final String result = qcodec.encode((String) null, "charset");
        assertNull(result, "Result should be null");
    }

    @Test
    void testInvalidEncoding() {
        assertThrows(UnsupportedCharsetException.class, () -> new QCodec("NONSENSE"));
    }

    @Test
    void testEncodeBlanksFlagToggles() throws Exception {
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

        final String encoded = qcodec.encode(plain);
        assertEquals("=?UTF-8?Q?=3F=5F=3D=0D=0A?=", encoded, "Unsafe chars Q encoding test");
        assertEquals(plain, qcodec.decode(encoded), "Unsafe chars Q decoding test");
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianMessage = constructString(RUSSIAN_STUFF_UNICODE);
        final String swissGermanMessage = constructString(SWISS_GERMAN_STUFF_UNICODE);

        final QCodec qcodec = new QCodec(CharEncoding.UTF_8);

        // Non-ASCII text is encoded as UTF-8 quoted-printable.
        assertEquals("=?UTF-8?Q?=D0=92=D1=81=D0=B5=D0=BC=5F=D0=BF=D1=80=D0=B8=D0=B2=D0=B5=D1=82?=",
                qcodec.encode(russianMessage));
        assertEquals("=?UTF-8?Q?Gr=C3=BCezi=5Fz=C3=A4m=C3=A4?=",
                qcodec.encode(swissGermanMessage));

        // Encoding then decoding restores the original text.
        assertEquals(russianMessage, qcodec.decode(qcodec.encode(russianMessage)));
        assertEquals(swissGermanMessage, qcodec.decode(qcodec.encode(swissGermanMessage)));
    }

}
