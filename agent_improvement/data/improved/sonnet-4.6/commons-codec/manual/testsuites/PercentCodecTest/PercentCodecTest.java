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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PercentCodec}, covering percent-encoding and decoding per RFC 3986:
 * safe/unsafe character handling, plus-for-space substitution, configurable always-encode
 * character sets, and null/empty input edge cases.
 */
class PercentCodecTest {

    /** Greek lowercase letters alpha through zeta (α β γ δ ε ζ), used in unsafe-char encoding tests. */
    private static final String GREEK_LETTERS_ALPHA_TO_ZETA = "αβγδεζ";

    @Test
    void testBasicEncodeDecode() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        final String input = "abcdABCD";
        final byte[] encoded = percentCodec.encode(input.getBytes(StandardCharsets.UTF_8));
        final String encodedS = new String(encoded, StandardCharsets.UTF_8);
        final byte[] decoded = percentCodec.decode(encoded);
        final String decodedS = new String(decoded, StandardCharsets.UTF_8);
        // Pure ASCII input contains no characters requiring encoding, so it passes through unchanged
        assertEquals(input, encodedS, "Basic PercentCodec encoding test");
        assertEquals(input, decodedS, "Basic PercentCodec decoding test");
    }

    @Test
    @Disabled // TODO Should be removed?
    void testBasicSpace() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        final String input = " ";
        final byte[] encoded = percentCodec.encode(input.getBytes(StandardCharsets.UTF_8));
        assertArrayEquals("%20".getBytes(StandardCharsets.UTF_8), encoded);
    }

    @Test
    void testConfigurablePercentEncoder() throws Exception {
        // 'abcdef' bytes are configured to always be encoded; other safe ASCII chars (123_-.*) are left as-is
        final String input = "abc123_-.*αβ";
        final PercentCodec percentCodec = new PercentCodec("abcdef".getBytes(StandardCharsets.UTF_8), false);
        final byte[] encoded = percentCodec.encode(input.getBytes(StandardCharsets.UTF_8));
        final String encodedS = new String(encoded, StandardCharsets.UTF_8);
        assertEquals("%61%62%63123_-.*%CE%B1%CE%B2", encodedS, "Configurable PercentCodec encoding test");
        final byte[] decoded = percentCodec.decode(encoded);
        assertEquals(input, new String(decoded, StandardCharsets.UTF_8), "Configurable PercentCodec decoding test");
    }

    @Test
    void testDecodeInvalidEncodedResultDecoding() throws Exception {
        // Encoding Greek letters α β produces multi-byte percent sequences;
        // truncating the last byte leaves an incomplete percent sequence that must raise DecoderException
        final String inputS = "αβ";
        final PercentCodec percentCodec = new PercentCodec();
        final byte[] encoded = percentCodec.encode(inputS.getBytes(StandardCharsets.UTF_8));
        try {
            percentCodec.decode(Arrays.copyOf(encoded, encoded.length - 1)); // exclude one byte
        } catch (final Exception e) {
            assertTrue(e instanceof DecoderException && e.getCause() instanceof ArrayIndexOutOfBoundsException);
        }
    }

    @Test
    void testDecodeNullObject() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        assertNull(percentCodec.decode((Object) null));
    }

    @Test
    void testDecodeUnsupportedObject() {
        final PercentCodec percentCodec = new PercentCodec();
        assertThrows(DecoderException.class, () -> percentCodec.decode("test"));
    }

    @Test
    void testEncodeNullObject() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        assertNull(percentCodec.encode((Object) null));
    }

    @Test
    void testEncodeUnsupportedObject() {
        final PercentCodec percentCodec = new PercentCodec();
        assertThrows(EncoderException.class, () -> percentCodec.encode("test"));
    }

    @Test
    void testInvalidByte() throws Exception {
        // Negative byte values are outside the valid US-ASCII range and must be rejected at construction
        final byte[] invalid = { (byte) -1, (byte) 'A' };
        assertThrows(IllegalArgumentException.class, () -> new PercentCodec(invalid, true));
    }

    @Test
    void testPercentEncoderDecoderWithNullOrEmptyInput() throws Exception {
        final PercentCodec percentCodec = new PercentCodec(null, true);
        assertNull(percentCodec.encode(null), "Null input value encoding test");
        assertNull(percentCodec.decode(null), "Null input value decoding test");
        final byte[] emptyInput = "".getBytes(StandardCharsets.UTF_8);
        assertEquals(emptyInput, percentCodec.encode(emptyInput), "Empty input value encoding test");
        assertArrayEquals(emptyInput, percentCodec.decode(emptyInput), "Empty input value decoding test");
    }

    @Test
    void testPercentEncoderDecoderWithPlusForSpace() throws Exception {
        // With plusForSpace=true, spaces are encoded as '+' rather than '%20'
        final String input = "a b c d";
        final PercentCodec percentCodec = new PercentCodec(null, true);
        final byte[] encoded = percentCodec.encode(input.getBytes(StandardCharsets.UTF_8));
        final String encodedS = new String(encoded, StandardCharsets.UTF_8);
        assertEquals("a+b+c+d", encodedS, "PercentCodec plus for space encoding test");
        final byte[] decoded = percentCodec.decode(encoded);
        assertEquals(input, new String(decoded, StandardCharsets.UTF_8), "PercentCodec plus for space decoding test");
    }

    @Test
    void testSafeCharEncodeDecodeObject() throws Exception {
        // Safe ASCII chars (alphanumerics and _-.*) must pass through encode/decode without modification
        final PercentCodec percentCodec = new PercentCodec(null, true);
        final String input = "abc123_-.*";
        final Object encoded = percentCodec.encode((Object) input.getBytes(StandardCharsets.UTF_8));
        final String encodedS = new String((byte[]) encoded, StandardCharsets.UTF_8);
        final Object decoded = percentCodec.decode(encoded);
        final String decodedS = new String((byte[]) decoded, StandardCharsets.UTF_8);
        assertEquals(input, encodedS, "Basic PercentCodec safe char encoding test");
        assertEquals(input, decodedS, "Basic PercentCodec safe char decoding test");
    }

    @Test
    void testUnsafeCharEncodeDecode() throws Exception {
        // Non-ASCII Greek letters and '%' are percent-encoded; trailing space is left as-is by the default codec
        final String input = GREEK_LETTERS_ALPHA_TO_ZETA + "% ";
        final PercentCodec percentCodec = new PercentCodec();
        final byte[] encoded = percentCodec.encode(input.getBytes(StandardCharsets.UTF_8));
        final String encodedS = new String(encoded, StandardCharsets.UTF_8);
        final byte[] decoded = percentCodec.decode(encoded);
        final String decodedS = new String(decoded, StandardCharsets.UTF_8);
        assertEquals("%CE%B1%CE%B2%CE%B3%CE%B4%CE%B5%CE%B6%25 ", encodedS, "Basic PercentCodec unsafe char encoding test");
        assertEquals(input, decodedS, "Basic PercentCodec unsafe char decoding test");
    }

}
