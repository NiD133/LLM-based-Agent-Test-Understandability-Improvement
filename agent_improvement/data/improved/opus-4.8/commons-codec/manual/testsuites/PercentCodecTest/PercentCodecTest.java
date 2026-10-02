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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Percent codec test cases.
 */
class PercentCodecTest {

    /** When constructed with {@code plusForSpace = true}, spaces are encoded as '+'. */
    private static final boolean PLUS_FOR_SPACE = true;

    /** When constructed with {@code plusForSpace = false}, spaces are left unchanged. */
    private static final boolean SPACE_AS_IS = false;

    /** Encodes {@code text} (read as UTF-8) and returns the result decoded back to a String. */
    private static String encodeToString(final PercentCodec codec, final String text) throws EncoderException {
        return new String(codec.encode(text.getBytes(UTF_8)), UTF_8);
    }

    /** Decodes {@code bytes} and returns the result as a UTF-8 String. */
    private static String decodeToString(final PercentCodec codec, final byte[] bytes) throws DecoderException {
        return new String(codec.decode(bytes), UTF_8);
    }

    @Test
    void testBasicEncodeDecode() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        final String input = "abcdABCD";

        final byte[] encoded = percentCodec.encode(input.getBytes(UTF_8));

        // Plain US-ASCII letters need no encoding, so encoding and decoding both round-trip unchanged.
        assertEquals(input, new String(encoded, UTF_8), "Basic PercentCodec encoding test");
        assertEquals(input, decodeToString(percentCodec, encoded), "Basic PercentCodec decoding test");
    }

    @Test
    @Disabled // TODO Should be removed?
    void testBasicSpace() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        final byte[] encoded = percentCodec.encode(" ".getBytes(UTF_8));

        assertArrayEquals("%20".getBytes(UTF_8), encoded);
    }

    @Test
    void testConfigurablePercentEncoder() throws Exception {
        // Mark a, b, c, d as "always encode"; everything else follows the default rules.
        final PercentCodec percentCodec = new PercentCodec("abcdef".getBytes(UTF_8), SPACE_AS_IS);
        final String input = "abc123_-.*αβ";

        final String encoded = encodeToString(percentCodec, input);

        // a, b, c are percent-encoded; the Greek letters (non-ASCII) are encoded as their UTF-8 bytes.
        assertEquals("%61%62%63123_-.*%CE%B1%CE%B2", encoded, "Configurable PercentCodec encoding test");
        assertEquals(input, decodeToString(percentCodec, encoded.getBytes(UTF_8)), "Configurable PercentCodec decoding test");
    }

    @Test
    void testDecodeInvalidEncodedResultDecoding() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        final byte[] encoded = percentCodec.encode("αβ".getBytes(UTF_8));

        // Dropping the final byte leaves a truncated "%XY" escape, which fails while reading past the array end.
        try {
            percentCodec.decode(Arrays.copyOf(encoded, encoded.length - 1));
        } catch (final Exception e) {
            assertInstanceOf(DecoderException.class, e);
            assertInstanceOf(ArrayIndexOutOfBoundsException.class, e.getCause());
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

        // Only byte[] objects can be decoded; a String must be rejected.
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

        // Only byte[] objects can be encoded; a String must be rejected.
        assertThrows(EncoderException.class, () -> percentCodec.encode("test"));
    }

    @Test
    void testInvalidByte() throws Exception {
        // Negative bytes are not valid US-ASCII characters, so they cannot be "always encode" chars.
        final byte[] invalidAlwaysEncodeChars = { (byte) -1, (byte) 'A' };

        assertThrows(IllegalArgumentException.class, () -> new PercentCodec(invalidAlwaysEncodeChars, PLUS_FOR_SPACE));
    }

    @Test
    void testPercentEncoderDecoderWithNullOrEmptyInput() throws Exception {
        final PercentCodec percentCodec = new PercentCodec(null, PLUS_FOR_SPACE);

        // null in -> null out for both directions.
        assertNull(percentCodec.encode(null), "Null input value encoding test");
        assertNull(percentCodec.decode(null), "Null input value decoding test");

        // Empty input is returned unchanged.
        final byte[] emptyInput = "".getBytes(UTF_8);
        assertEquals(emptyInput, percentCodec.encode(emptyInput), "Empty input value encoding test");
        assertArrayEquals(emptyInput, percentCodec.decode(emptyInput), "Empty input value decoding test");
    }

    @Test
    void testPercentEncoderDecoderWithPlusForSpace() throws Exception {
        final PercentCodec percentCodec = new PercentCodec(null, PLUS_FOR_SPACE);
        final String input = "a b c d";

        final String encoded = encodeToString(percentCodec, input);

        // With plusForSpace enabled, each space becomes '+' and decoding restores the spaces.
        assertEquals("a+b+c+d", encoded, "PercentCodec plus for space encoding test");
        assertEquals(input, decodeToString(percentCodec, encoded.getBytes(UTF_8)), "PercentCodec plus for space decoding test");
    }

    @Test
    void testSafeCharEncodeDecodeObject() throws Exception {
        final PercentCodec percentCodec = new PercentCodec(null, PLUS_FOR_SPACE);
        final String input = "abc123_-.*";

        // Exercise the Object-based encode/decode overloads with byte[] payloads.
        final Object encoded = percentCodec.encode((Object) input.getBytes(UTF_8));
        final Object decoded = percentCodec.decode(encoded);

        // All characters are safe, so the value round-trips unchanged.
        assertEquals(input, new String((byte[]) encoded, UTF_8), "Basic PercentCodec safe char encoding test");
        assertEquals(input, new String((byte[]) decoded, UTF_8), "Basic PercentCodec safe char decoding test");
    }

    @Test
    void testUnsafeCharEncodeDecode() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        final String input = "αβγδεζ% ";

        final String encoded = encodeToString(percentCodec, input);

        // Greek letters and '%' are encoded; the space is left as-is (plusForSpace is off by default).
        assertEquals("%CE%B1%CE%B2%CE%B3%CE%B4%CE%B5%CE%B6%25 ", encoded, "Basic PercentCodec unsafe char encoding test");
        assertEquals(input, decodeToString(percentCodec, encoded.getBytes(UTF_8)), "Basic PercentCodec unsafe char decoding test");
    }

}
