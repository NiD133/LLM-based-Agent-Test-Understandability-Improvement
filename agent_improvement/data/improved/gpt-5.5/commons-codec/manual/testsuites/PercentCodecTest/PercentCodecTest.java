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
 * Percent codec test cases.
 */
class PercentCodecTest {

    private static final String ASCII_SAFE_TEXT = "abcdABCD";
    private static final String CONFIGURABLE_INPUT = "abc123_-.*\u03B1\u03B2";
    private static final String GREEK_ALPHA_BETA = "\u03B1\u03B2";
    private static final String SAFE_CHARS = "abc123_-.*";
    private static final String UNSAFE_TEXT = "\u03B1\u03B2\u03B3\u03B4\u03B5\u03B6% ";

    private byte[] utf8(final String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }

    private String utf8String(final byte[] value) {
        return new String(value, StandardCharsets.UTF_8);
    }

    @Test
    void testBasicEncodeDecode() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        final String input = ASCII_SAFE_TEXT;

        final byte[] encoded = percentCodec.encode(utf8(input));
        final String encodedS = utf8String(encoded);
        final byte[] decoded = percentCodec.decode(encoded);
        final String decodedS = utf8String(decoded);

        assertEquals(input, encodedS, "Basic PercentCodec encoding test");
        assertEquals(input, decodedS, "Basic PercentCodec decoding test");
    }

    @Test
    @Disabled // TODO Should be removed?
    void testBasicSpace() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        final String input = " ";

        final byte[] encoded = percentCodec.encode(utf8(input));

        assertArrayEquals(utf8("%20"), encoded);
    }

    @Test
    void testConfigurablePercentEncoder() throws Exception {
        final String input = CONFIGURABLE_INPUT;
        final PercentCodec percentCodec = new PercentCodec(utf8("abcdef"), false);

        final byte[] encoded = percentCodec.encode(utf8(input));
        final String encodedS = utf8String(encoded);
        final byte[] decoded = percentCodec.decode(encoded);

        assertEquals("%61%62%63123_-.*%CE%B1%CE%B2", encodedS, "Configurable PercentCodec encoding test");
        assertEquals(utf8String(decoded), input, "Configurable PercentCodec decoding test");
    }

    @Test
    void testDecodeInvalidEncodedResultDecoding() throws Exception {
        final String inputS = GREEK_ALPHA_BETA;
        final PercentCodec percentCodec = new PercentCodec();
        final byte[] encoded = percentCodec.encode(utf8(inputS));
        final byte[] truncatedEncodedInput = Arrays.copyOf(encoded, encoded.length - 1);

        try {
            percentCodec.decode(truncatedEncodedInput);
        } catch (final Exception e) {
            assertTrue(DecoderException.class.isInstance(e) && ArrayIndexOutOfBoundsException.class.isInstance(e.getCause()));
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
        final byte[] invalid = { (byte) -1, (byte) 'A' };

        assertThrows(IllegalArgumentException.class, () -> new PercentCodec(invalid, true));
    }

    @Test
    void testPercentEncoderDecoderWithNullOrEmptyInput() throws Exception {
        final PercentCodec percentCodec = new PercentCodec(null, true);
        final byte[] emptyInput = utf8("");

        assertNull(percentCodec.encode(null), "Null input value encoding test");
        assertNull(percentCodec.decode(null), "Null input value decoding test");
        assertEquals(percentCodec.encode(emptyInput), emptyInput, "Empty input value encoding test");
        assertArrayEquals(percentCodec.decode(emptyInput), emptyInput, "Empty input value decoding test");
    }

    @Test
    void testPercentEncoderDecoderWithPlusForSpace() throws Exception {
        final String input = "a b c d";
        final PercentCodec percentCodec = new PercentCodec(null, true);

        final byte[] encoded = percentCodec.encode(utf8(input));
        final String encodedS = utf8String(encoded);
        final byte[] decode = percentCodec.decode(encoded);

        assertEquals("a+b+c+d", encodedS, "PercentCodec plus for space encoding test");
        assertEquals(utf8String(decode), input, "PercentCodec plus for space decoding test");
    }

    @Test
    void testSafeCharEncodeDecodeObject() throws Exception {
        final PercentCodec percentCodec = new PercentCodec(null, true);
        final String input = SAFE_CHARS;

        final Object encoded = percentCodec.encode((Object) utf8(input));
        final String encodedS = utf8String((byte[]) encoded);
        final Object decoded = percentCodec.decode(encoded);
        final String decodedS = utf8String((byte[]) decoded);

        assertEquals(input, encodedS, "Basic PercentCodec safe char encoding test");
        assertEquals(input, decodedS, "Basic PercentCodec safe char decoding test");
    }

    @Test
    void testUnsafeCharEncodeDecode() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        final String input = UNSAFE_TEXT;

        final byte[] encoded = percentCodec.encode(utf8(input));
        final String encodedS = utf8String(encoded);
        final byte[] decoded = percentCodec.decode(encoded);
        final String decodedS = utf8String(decoded);

        assertEquals("%CE%B1%CE%B2%CE%B3%CE%B4%CE%B5%CE%B6%25 ", encodedS, "Basic PercentCodec unsafe char encoding test");
        assertEquals(input, decodedS, "Basic PercentCodec unsafe char decoding test");
    }

}
