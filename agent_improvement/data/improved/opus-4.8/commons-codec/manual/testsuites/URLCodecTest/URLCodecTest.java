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
 * Tests for {@link URLCodec}, the 'www-form-urlencoded' encoder/decoder.
 */
class URLCodecTest {

    /**
     * Unicode code points for the Swiss-German phrase "Gr&uuml;ezi_z&auml;m&auml;".
     * Used to exercise encoding/decoding of non-ASCII text.
     */
    static final int[] SWISS_GERMAN_STUFF_UNICODE = { 0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A, 0xE4, 0x6D, 0xE4 };

    /**
     * Unicode code points for the Russian phrase "&#x412;&#x441;&#x435;&#x43C;_&#x43F;&#x440;&#x438;&#x432;&#x435;&#x442;".
     * Used to exercise encoding/decoding of non-ASCII text.
     */
    static final int[] RUSSIAN_STUFF_UNICODE = { 0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438, 0x432, 0x435, 0x442 };

    /**
     * Builds a String from the given array of Unicode code points.
     *
     * @param unicodeChars the code points, or {@code null} for an empty result.
     * @return the assembled String.
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

        // A lone escape char with no following hex digits.
        assertThrows(DecoderException.class, () -> urlCodec.decode("%"));
        // Only one hex digit after the escape char.
        assertThrows(DecoderException.class, () -> urlCodec.decode("%A"));
        // Invalid first hex digit after the escape char.
        assertThrows(DecoderException.class, () -> urlCodec.decode("%WW"));
        // Invalid second hex digit after the escape char.
        assertThrows(DecoderException.class, () -> urlCodec.decode("%0W"));
        validateState(urlCodec);
    }

    @Test
    void testDecodeInvalidContent() throws DecoderException {
        // Bytes that contain no escape sequences should be returned unchanged.
        final String swissGerman = constructString(SWISS_GERMAN_STUFF_UNICODE);
        final URLCodec urlCodec = new URLCodec();
        final byte[] input = swissGerman.getBytes(StandardCharsets.ISO_8859_1);

        final byte[] output = urlCodec.decode(input);

        assertEquals(input.length, output.length);
        for (int i = 0; i < input.length; i++) {
            assertEquals(input[i], output[i]);
        }
        validateState(urlCodec);
    }

    @Test
    void testDecodeObjects() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String encodedText = "Hello+there%21";

        // Decoding a String passed as an Object yields a String.
        final String decodedFromString = (String) urlCodec.decode((Object) encodedText);
        assertEquals("Hello there!", decodedFromString, "Basic URL decoding test");

        // Decoding a byte[] passed as an Object yields a byte[].
        final byte[] encodedBytes = encodedText.getBytes(StandardCharsets.UTF_8);
        final byte[] decodedBytes = (byte[]) urlCodec.decode((Object) encodedBytes);
        assertEquals("Hello there!", new String(decodedBytes), "Basic URL decoding test");

        // Decoding a null Object returns null.
        final Object decodedFromNull = urlCodec.decode((Object) null);
        assertNull(decodedFromNull, "Decoding a null Object should return null");

        // Decoding an unsupported type throws.
        assertThrows(DecoderException.class, () -> urlCodec.decode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
        validateState(urlCodec);
    }

    @Test
    void testDecodeStringWithNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String nullInput = null;

        final String result = urlCodec.decode(nullInput, "charset");

        assertNull(result, "Result should be null");
    }

    @Test
    void testDecodeWithNullArray() throws Exception {
        final byte[] nullInput = null;

        final byte[] result = URLCodec.decodeUrl(nullInput);

        assertNull(result, "Result should be null");
    }

    @Test
    void testDefaultEncoding() throws Exception {
        final String plain = "Hello there!";
        final URLCodec urlCodec = new URLCodec("UnicodeBig");

        urlCodec.encode(plain); // To work around a weird quirk in Java 1.2.2

        // Encoding with the explicit default charset must match encoding with the implicit one.
        final String encodedWithExplicitCharset = urlCodec.encode(plain, "UnicodeBig");
        final String encodedWithDefaultCharset = urlCodec.encode(plain);
        assertEquals(encodedWithExplicitCharset, encodedWithDefaultCharset);
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
        final byte[] nullInput = null;

        final byte[] encoded = urlCodec.encode(nullInput);

        assertNull(encoded, "Encoding a null string should return null");
        validateState(urlCodec);
    }

    @Test
    void testEncodeObjects() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plain = "Hello there!";

        // Encoding a String passed as an Object yields a String.
        final String encodedFromString = (String) urlCodec.encode((Object) plain);
        assertEquals("Hello+there%21", encodedFromString, "Basic URL encoding test");

        // Encoding a byte[] passed as an Object yields a byte[].
        final byte[] plainBytes = plain.getBytes(StandardCharsets.UTF_8);
        final byte[] encodedBytes = (byte[]) urlCodec.encode((Object) plainBytes);
        assertEquals("Hello+there%21", new String(encodedBytes), "Basic URL encoding test");

        // Encoding a null Object returns null.
        final Object encodedFromNull = urlCodec.encode((Object) null);
        assertNull(encodedFromNull, "Encoding a null Object should return null");

        // Encoding an unsupported type throws.
        assertThrows(EncoderException.class, () -> urlCodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
        validateState(urlCodec);
    }

    @Test
    void testEncodeStringWithNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String nullInput = null;

        final String result = urlCodec.encode(nullInput, "charset");

        assertNull(result, "Result should be null");
    }

    @Test
    void testEncodeUrlWithNullBitSet() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plain = "Hello there!";

        // A null BitSet falls back to the default www-form-url safe characters.
        final String encoded = new String(URLCodec.encodeUrl(null, plain.getBytes(StandardCharsets.UTF_8)));

        assertEquals("Hello+there%21", encoded, "Basic URL encoding test");
        assertEquals(plain, urlCodec.decode(encoded), "Basic URL decoding test");
        validateState(urlCodec);
    }

    @Test
    void testInvalidEncoding() {
        final URLCodec urlCodec = new URLCodec("NONSENSE");
        final String plain = "Hello there!";

        // An unsupported charset makes both encode and decode fail.
        assertThrows(EncoderException.class, () -> urlCodec.encode(plain), "We set the encoding to a bogus NONSENSE value");
        assertThrows(DecoderException.class, () -> urlCodec.decode(plain), "We set the encoding to a bogus NONSENSE value");
        validateState(urlCodec);
    }

    @Test
    void testSafeCharEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plain = "abc123_-.*";

        final String encoded = urlCodec.encode(plain);

        // Safe characters pass through encoding unchanged.
        assertEquals(plain, encoded, "Safe chars URL encoding test");
        assertEquals(plain, urlCodec.decode(encoded), "Safe chars URL decoding test");
        validateState(urlCodec);
    }

    @Test
    void testUnsafeEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plain = "~!@#$%^&()+{}\"\\;:`,/[]";

        final String encoded = urlCodec.encode(plain);

        // Every unsafe character is percent-escaped.
        assertEquals("%7E%21%40%23%24%25%5E%26%28%29%2B%7B%7D%22%5C%3B%3A%60%2C%2F%5B%5D", encoded,
                "Unsafe chars URL encoding test");
        assertEquals(plain, urlCodec.decode(encoded), "Unsafe chars URL decoding test");
        validateState(urlCodec);
    }

    @Test
    void testUTF8RoundTrip() throws Exception {
        final String russian = constructString(RUSSIAN_STUFF_UNICODE);
        final String swissGerman = constructString(SWISS_GERMAN_STUFF_UNICODE);

        final URLCodec urlCodec = new URLCodec();
        validateState(urlCodec);

        // Non-ASCII text is UTF-8 encoded and then percent-escaped.
        assertEquals("%D0%92%D1%81%D0%B5%D0%BC_%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82",
                urlCodec.encode(russian, CharEncoding.UTF_8));
        assertEquals("Gr%C3%BCezi_z%C3%A4m%C3%A4", urlCodec.encode(swissGerman, CharEncoding.UTF_8));

        // Encoding then decoding with UTF-8 must round-trip back to the original text.
        assertEquals(russian, urlCodec.decode(urlCodec.encode(russian, CharEncoding.UTF_8), CharEncoding.UTF_8));
        assertEquals(swissGerman, urlCodec.decode(urlCodec.encode(swissGerman, CharEncoding.UTF_8), CharEncoding.UTF_8));
        validateState(urlCodec);
    }

    /**
     * Placeholder for additional state checks on the codec; intentionally a no-op,
     * preserved so each test documents where post-conditions would be verified.
     *
     * @param urlCodec the codec under test.
     */
    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }
}
