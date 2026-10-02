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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link URLCodec}, which implements the 'www-form-urlencoded' encoding scheme.
 * Covers encoding and decoding of strings and byte arrays, null handling, invalid input,
 * charset selection, and multi-language (Unicode) round-trips.
 */
class URLCodecTest {

    // Unicode code points for "Grüezi_zämä" (Swiss-German greeting)
    static final int[] SWISS_GERMAN_STUFF_UNICODE = { 0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4 };

    // Unicode code points for "Всем_привет" (Russian greeting)
    static final int[] RUSSIAN_STUFF_UNICODE = { 0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442 };

    /**
     * Builds a Java {@code String} from an array of Unicode code points (as ints).
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
        final URLCodec urlCodec = new URLCodec();
        final String plain = "Hello there!";
        final String encoded = urlCodec.encode(plain);
        assertEquals("Hello+there%21", encoded, "Basic URL encoding test");
        assertEquals(plain, urlCodec.decode(encoded), "Basic URL decoding test");
        validateState(urlCodec);
    }

    @Test
    void testDecodeInvalid() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        // Truncated escape sequences: less than two hex digits follow '%'
        assertThrows(DecoderException.class, () -> urlCodec.decode("%"));
        assertThrows(DecoderException.class, () -> urlCodec.decode("%A"));
        // Non-hex character as the first digit after '%'
        assertThrows(DecoderException.class, () -> urlCodec.decode("%WW"));
        // Non-hex character as the second digit after '%'
        assertThrows(DecoderException.class, () -> urlCodec.decode("%0W"));
        validateState(urlCodec);
    }

    @Test
    void testDecodeInvalidContent() throws DecoderException {
        final String swissGermanMsg = constructString(SWISS_GERMAN_STUFF_UNICODE);
        final URLCodec urlCodec = new URLCodec();
        // ISO-8859-1 bytes are passed directly; decodeUrl should return them unchanged
        final byte[] inputBytes = swissGermanMsg.getBytes(StandardCharsets.ISO_8859_1);
        final byte[] outputBytes = urlCodec.decode(inputBytes);
        assertEquals(inputBytes.length, outputBytes.length);
        for (int i = 0; i < inputBytes.length; i++) {
            assertEquals(inputBytes[i], outputBytes[i]);
        }
        validateState(urlCodec);
    }

    @Test
    void testDecodeObjects() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plain = "Hello+there%21";

        // Decode a String object
        String decoded = (String) urlCodec.decode((Object) plain);
        assertEquals("Hello there!", decoded, "Basic URL decoding test");

        // Decode a byte[] object
        final byte[] plainBytes = plain.getBytes(StandardCharsets.UTF_8);
        final byte[] decodedBytes = (byte[]) urlCodec.decode((Object) plainBytes);
        decoded = new String(decodedBytes);
        assertEquals("Hello there!", decoded, "Basic URL decoding test");

        // Decoding null should return null
        final Object result = urlCodec.decode((Object) null);
        assertNull(result, "Decoding a null Object should return null");

        // Unsupported type should throw DecoderException
        assertThrows(DecoderException.class, () -> urlCodec.decode(Double.valueOf(3.0d)), "Trying to url encode a Double object should cause an exception.");
        validateState(urlCodec);
    }

    @Test
    void testDecodeStringWithNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String test = null;
        final String result = urlCodec.decode(test, "charset");
        assertNull(result, "Result should be null");
    }

    @Test
    void testDecodeWithNullArray() throws Exception {
        final byte[] plain = null;
        final byte[] result = URLCodec.decodeUrl(plain);
        assertNull(result, "Result should be null");
    }

    @Test
    void testDefaultEncoding() throws Exception {
        final String plain = "Hello there!";
        final URLCodec urlCodec = new URLCodec("UnicodeBig");
        urlCodec.encode(plain); // To work around a weird quirk in Java 1.2.2
        final String encoded1 = urlCodec.encode(plain, "UnicodeBig");
        final String encoded2 = urlCodec.encode(plain);
        assertEquals(encoded1, encoded2);
        validateState(urlCodec);
    }

    @Test
    void testEncodeDecodeNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        assertNull(urlCodec.encode((String) null), "Null string URL encoding test");
        assertNull(urlCodec.decode((String) null), "Null string URL decoding test");
        validateState(urlCodec);
    }

    @Test
    void testEncodeNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final byte[] plain = null;
        final byte[] encoded = urlCodec.encode(plain);
        assertNull(encoded, "Encoding a null string should return null");
        validateState(urlCodec);
    }

    @Test
    void testEncodeObjects() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plain = "Hello there!";

        // Encode a String object
        String encoded = (String) urlCodec.encode((Object) plain);
        assertEquals("Hello+there%21", encoded, "Basic URL encoding test");

        // Encode a byte[] object
        final byte[] plainBytes = plain.getBytes(StandardCharsets.UTF_8);
        final byte[] encodedBytes = (byte[]) urlCodec.encode((Object) plainBytes);
        encoded = new String(encodedBytes);
        assertEquals("Hello+there%21", encoded, "Basic URL encoding test");

        // Encoding null should return null
        final Object result = urlCodec.encode((Object) null);
        assertNull(result, "Encoding a null Object should return null");

        // Unsupported type should throw EncoderException
        assertThrows(EncoderException.class, () -> urlCodec.encode(Double.valueOf(3.0d)), "Trying to url encode a Double object should cause an exception.");
        validateState(urlCodec);
    }

    @Test
    void testEncodeStringWithNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String test = null;
        final String result = urlCodec.encode(test, "charset");
        assertNull(result, "Result should be null");
    }

    @Test
    void testEncodeUrlWithNullBitSet() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plain = "Hello there!";
        // Passing null for the safe-character BitSet should fall back to the default safe set
        final String encoded = new String(URLCodec.encodeUrl(null, plain.getBytes(StandardCharsets.UTF_8)));
        assertEquals("Hello+there%21", encoded, "Basic URL encoding test");
        assertEquals(plain, urlCodec.decode(encoded), "Basic URL decoding test");
        validateState(urlCodec);
    }

    @Test
    void testInvalidEncoding() {
        final URLCodec urlCodec = new URLCodec("NONSENSE");
        final String plain = "Hello there!";
        assertThrows(EncoderException.class, () -> urlCodec.encode(plain), "We set the encoding to a bogus NONSENSE value");
        assertThrows(DecoderException.class, () -> urlCodec.decode(plain), "We set the encoding to a bogus NONSENSE value");
        validateState(urlCodec);
    }

    @Test
    void testSafeCharEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        // Characters in the URL-safe set must not be percent-encoded
        final String plain = "abc123_-.*";
        final String encoded = urlCodec.encode(plain);
        assertEquals(plain, encoded, "Safe chars URL encoding test");
        assertEquals(plain, urlCodec.decode(encoded), "Safe chars URL decoding test");
        validateState(urlCodec);
    }

    @Test
    void testUnsafeEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        // Characters outside the safe set must be percent-encoded
        final String plain = "~!@#$%^&()+{}\"\\;:`,/[]";
        final String encoded = urlCodec.encode(plain);
        assertEquals("%7E%21%40%23%24%25%5E%26%28%29%2B%7B%7D%22%5C%3B%3A%60%2C%2F%5B%5D", encoded, "Unsafe chars URL encoding test");
        assertEquals(plain, urlCodec.decode(encoded), "Unsafe chars URL decoding test");
        validateState(urlCodec);
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russianMsg = constructString(RUSSIAN_STUFF_UNICODE);
        final String swissGermanMsg = constructString(SWISS_GERMAN_STUFF_UNICODE);

        final URLCodec urlCodec = new URLCodec();
        validateState(urlCodec);

        // Verify UTF-8 percent-encoding produces the expected ASCII output
        assertEquals("%D0%92%D1%81%D0%B5%D0%BC_%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82", urlCodec.encode(russianMsg, CharEncoding.UTF_8));
        assertEquals("Gr%C3%BCezi_z%C3%A4m%C3%A4", urlCodec.encode(swissGermanMsg, CharEncoding.UTF_8));

        // Verify that encoding then decoding with UTF-8 recovers the original string
        assertEquals(russianMsg, urlCodec.decode(urlCodec.encode(russianMsg, CharEncoding.UTF_8), CharEncoding.UTF_8));
        assertEquals(swissGermanMsg, urlCodec.decode(urlCodec.encode(swissGermanMsg, CharEncoding.UTF_8), CharEncoding.UTF_8));
        validateState(urlCodec);
    }

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }
}
