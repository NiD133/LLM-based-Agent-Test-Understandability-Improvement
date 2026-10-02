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
 * Tests for {@link BCodec}, which encodes and decodes strings using Base64 as
 * described by RFC 1522 "encoded-word" headers.
 */
class BCodecTest {

    /**
     * RFC 1522 encoded-word strings whose Base64 payload contains bit patterns
     * that are impossible in valid Base64 (non-zero trailing bits). The codec
     * must either silently tolerate them (lenient mode) or reject them with a
     * {@link DecoderException} (strict mode).
     */
    private static final String[] BASE64_IMPOSSIBLE_CASES = {
            "=?ASCII?B?ZE==?=",
            "=?ASCII?B?ZmC=?=",
            "=?ASCII?B?Zm9vYE==?=",
            "=?ASCII?B?Zm9vYmC=?=",
            "=?ASCII?B?AB==?="
    };

    /** Unicode code points for the Swiss-German greeting "Grüezi_zämä". */
    static final int[] SWISS_GERMAN_STUFF_UNICODE =
        { 0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4 };

    /** Unicode code points for the Russian greeting "Всем_привет". */
    static final int[] RUSSIAN_STUFF_UNICODE =
        { 0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442 };

    /** Builds a {@link String} from an array of Unicode code points. */
    private String constructString(final int[] unicodeChars) {
        final StringBuilder buffer = new StringBuilder();
        if (unicodeChars != null) {
            for (final int unicodeChar : unicodeChars) {
                buffer.append((char) unicodeChar);
            }
        }
        return buffer.toString();
    }

    // -------------------------------------------------------------------------
    // Strict / lenient decoding policy tests
    // -------------------------------------------------------------------------

    @Test
    void testBase64ImpossibleSamplesDefault() throws DecoderException {
        final BCodec codec = new BCodec();
        // The no-arg constructor uses lenient decoding by default.
        assertFalse(codec.isStrictDecoding());
        for (final String s : BASE64_IMPOSSIBLE_CASES) {
            codec.decode(s); // must not throw in lenient mode
        }
    }

    @Test
    void testBase64ImpossibleSamplesLenient() throws DecoderException {
        final BCodec codec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.LENIENT);
        assertFalse(codec.isStrictDecoding());
        for (final String s : BASE64_IMPOSSIBLE_CASES) {
            codec.decode(s); // must not throw in lenient mode
        }
    }

    @Test
    void testBase64ImpossibleSamplesStrict() {
        final BCodec codec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.STRICT);
        assertTrue(codec.isStrictDecoding());
        for (final String s : BASE64_IMPOSSIBLE_CASES) {
            // Every impossible sample must trigger a DecoderException in strict mode.
            assertThrows(DecoderException.class, () -> codec.decode(s));
        }
    }

    // -------------------------------------------------------------------------
    // Basic encode / decode round-trip tests
    // -------------------------------------------------------------------------

    @Test
    void testBasicEncodeDecode() throws Exception {
        final BCodec bcodec = new BCodec();
        final String plain = "Hello there";
        final String encoded = bcodec.encode(plain);
        assertEquals("=?UTF-8?B?SGVsbG8gdGhlcmU=?=", encoded, "Basic B encoding test");
        assertEquals(plain, bcodec.decode(encoded), "Basic B decoding test");
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianMessage    = constructString(RUSSIAN_STUFF_UNICODE);
        final String swissGermanMessage = constructString(SWISS_GERMAN_STUFF_UNICODE);

        final BCodec bcodec = new BCodec(CharEncoding.UTF_8);

        assertEquals("=?UTF-8?B?0JLRgdC10Lxf0L/RgNC40LLQtdGC?=", bcodec.encode(russianMessage));
        assertEquals("=?UTF-8?B?R3LDvGV6aV96w6Rtw6Q=?=", bcodec.encode(swissGermanMessage));

        assertEquals(russianMessage,    bcodec.decode(bcodec.encode(russianMessage)));
        assertEquals(swissGermanMessage, bcodec.decode(bcodec.encode(swissGermanMessage)));
    }

    // -------------------------------------------------------------------------
    // Null-input handling
    // -------------------------------------------------------------------------

    @Test
    void testEncodeDecodeNull() throws Exception {
        final BCodec bcodec = new BCodec();
        assertNull(bcodec.encode((String) null), "Null string B encoding test");
        assertNull(bcodec.decode((String) null), "Null string B decoding test");
    }

    @Test
    void testDecodeStringWithNull() throws Exception {
        final BCodec bcodec = new BCodec();
        final String test = null;
        final String result = bcodec.decode(test);
        assertNull(result, "Result should be null");
    }

    @Test
    void testEncodeStringWithNull() throws Exception {
        final BCodec bcodec = new BCodec();
        final String test = null;
        // The charset argument is irrelevant when the source string is null;
        // encode() must return null before trying to resolve the charset.
        final String result = bcodec.encode(test, "charset");
        assertNull(result, "Result should be null");
    }

    @Test
    void testNullInput() throws Exception {
        final BCodec bcodec = new BCodec();
        assertNull(bcodec.doDecoding(null));
        assertNull(bcodec.doEncoding(null));
    }

    // -------------------------------------------------------------------------
    // Object-level encode / decode (codec pipeline API)
    // -------------------------------------------------------------------------

    @Test
    void testEncodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();
        final String plain = "what not";
        final String encoded = (String) bcodec.encode((Object) plain);
        assertEquals("=?UTF-8?B?d2hhdCBub3Q=?=", encoded, "Basic B encoding test");

        assertNull(bcodec.encode((Object) null), "Encoding a null Object should return null");

        // Encoding a non-String object must throw EncoderException.
        assertThrows(EncoderException.class, () -> bcodec.encode(Double.valueOf(3.0d)),
            "Trying to url encode a Double object should cause an exception.");
    }

    @Test
    void testDecodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();
        final String encoded = "=?UTF-8?B?d2hhdCBub3Q=?=";
        final String plain = (String) bcodec.decode((Object) encoded);
        assertEquals("what not", plain, "Basic B decoding test");

        assertNull(bcodec.decode((Object) null), "Decoding a null Object should return null");

        // Decoding a non-String object must throw DecoderException.
        assertThrows(DecoderException.class, () -> bcodec.decode(Double.valueOf(3.0d)));
    }

    // -------------------------------------------------------------------------
    // Constructor / configuration tests
    // -------------------------------------------------------------------------

    @Test
    void testInvalidEncoding() {
        // Passing an unrecognised charset name to the constructor must throw immediately.
        assertThrows(UnsupportedCharsetException.class, () -> new BCodec("NONSENSE"));
    }
}
