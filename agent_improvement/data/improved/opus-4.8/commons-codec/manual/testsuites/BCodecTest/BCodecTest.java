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

import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BCodec}, the "B" (Base64) encoding of RFC 1522 MIME message headers.
 *
 * <p>An RFC 1522 "encoded-word" has the shape {@code =?<charset>?B?<base64>?=}. The codec
 * encodes plain text into that form and decodes it back again.</p>
 */
class BCodecTest {

    /**
     * Encoded-words whose Base64 payload has trailing bits that cannot form whole bytes.
     * A lenient codec decodes as much as it can; a strict codec rejects them.
     */
    private static final String[] BASE64_IMPOSSIBLE_CASES = {
            "=?ASCII?B?ZE==?=",
            "=?ASCII?B?ZmC=?=",
            "=?ASCII?B?Zm9vYE==?=",
            "=?ASCII?B?Zm9vYmC=?=",
            "=?ASCII?B?AB==?="
    };

    /** Unicode code points for the Swiss-German sample text "Grüezi_zämä". */
    static final int[] SWISS_GERMAN_STUFF_UNICODE =
        { 0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4 };

    /** Unicode code points for the Russian sample text "Всем_привет". */
    static final int[] RUSSIAN_STUFF_UNICODE =
        { 0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442 };

    /** Builds a String from an array of Unicode code points. */
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
    void testBase64ImpossibleSamplesDefault() throws DecoderException {
        // The no-arg constructor uses the lenient decoding policy by default.
        final BCodec codec = new BCodec();
        assertFalse(codec.isStrictDecoding(), "Default decoding should be lenient");

        // Lenient decoding accepts every "impossible" sample without throwing.
        for (final String impossibleCase : BASE64_IMPOSSIBLE_CASES) {
            codec.decode(impossibleCase);
        }
    }

    @Test
    void testBase64ImpossibleSamplesLenient() throws DecoderException {
        // Explicitly request the lenient policy.
        final BCodec codec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.LENIENT);
        assertFalse(codec.isStrictDecoding(), "Decoding should be lenient");

        // Lenient decoding accepts every "impossible" sample without throwing.
        for (final String impossibleCase : BASE64_IMPOSSIBLE_CASES) {
            codec.decode(impossibleCase);
        }
    }

    @Test
    void testBase64ImpossibleSamplesStrict() {
        // Explicitly request the strict policy.
        final BCodec codec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.STRICT);
        assertTrue(codec.isStrictDecoding(), "Decoding should be strict");

        // Strict decoding rejects every "impossible" sample with a DecoderException.
        for (final String impossibleCase : BASE64_IMPOSSIBLE_CASES) {
            assertThrows(DecoderException.class, () -> codec.decode(impossibleCase));
        }
    }

    @Test
    void testBasicEncodeDecode() throws Exception {
        final BCodec bcodec = new BCodec();
        final String plain = "Hello there";

        final String encoded = bcodec.encode(plain);
        assertEquals("=?UTF-8?B?SGVsbG8gdGhlcmU=?=", encoded, "Basic B encoding test");
        assertEquals(plain, bcodec.decode(encoded), "Basic B decoding test");
    }

    @Test
    void testDecodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();

        // A String passed as an Object is decoded to its plain form.
        final String encoded = "=?UTF-8?B?d2hhdCBub3Q=?=";
        final String plain = (String) bcodec.decode((Object) encoded);
        assertEquals("what not", plain, "Basic B decoding test");

        // A null Object decodes to null.
        final Object result = bcodec.decode((Object) null);
        assertNull(result, "Decoding a null Object should return null");

        // A non-String Object cannot be decoded.
        assertThrows(DecoderException.class, () -> bcodec.decode(Double.valueOf(3.0d)));
    }

    @Test
    void testDecodeStringWithNull() throws Exception {
        final BCodec bcodec = new BCodec();

        final String nullString = null;
        assertNull(bcodec.decode(nullString), "Decoding a null String should return null");
    }

    @Test
    void testEncodeDecodeNull() throws Exception {
        final BCodec bcodec = new BCodec();

        assertNull(bcodec.encode((String) null), "Null string B encoding test");
        assertNull(bcodec.decode((String) null), "Null string B decoding test");
    }

    @Test
    void testEncodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();

        // A String passed as an Object is encoded to its B form.
        final String plain = "what not";
        final String encoded = (String) bcodec.encode((Object) plain);
        assertEquals("=?UTF-8?B?d2hhdCBub3Q=?=", encoded, "Basic B encoding test");

        // A null Object encodes to null.
        final Object result = bcodec.encode((Object) null);
        assertNull(result, "Encoding a null Object should return null");

        // A non-String Object cannot be encoded.
        assertThrows(EncoderException.class, () -> bcodec.encode(Double.valueOf(3.0d)),
            "Trying to url encode a Double object should cause an exception.");
    }

    @Test
    void testEncodeStringWithNull() throws Exception {
        final BCodec bcodec = new BCodec();

        final String nullString = null;
        assertNull(bcodec.encode(nullString, "charset"), "Encoding a null String should return null");
    }

    @Test
    void testInvalidEncoding() {
        // Constructing with an unknown charset name fails fast.
        assertThrows(UnsupportedCharsetException.class, () -> new BCodec("NONSENSE"));
    }

    @Test
    void testNullInput() throws Exception {
        final BCodec bcodec = new BCodec();

        // The low-level byte hooks pass null straight through.
        assertNull(bcodec.doDecoding(null));
        assertNull(bcodec.doEncoding(null));
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianText = constructString(RUSSIAN_STUFF_UNICODE);
        final String swissGermanText = constructString(SWISS_GERMAN_STUFF_UNICODE);

        final BCodec bcodec = new BCodec(CharEncoding.UTF_8);

        // Encoding produces the expected UTF-8 encoded-words.
        assertEquals("=?UTF-8?B?0JLRgdC10Lxf0L/RgNC40LLQtdGC?=", bcodec.encode(russianText));
        assertEquals("=?UTF-8?B?R3LDvGV6aV96w6Rtw6Q=?=", bcodec.encode(swissGermanText));

        // Encoding then decoding returns the original text.
        assertEquals(russianText, bcodec.decode(bcodec.encode(russianText)));
        assertEquals(swissGermanText, bcodec.decode(bcodec.encode(swissGermanText)));
    }

}
