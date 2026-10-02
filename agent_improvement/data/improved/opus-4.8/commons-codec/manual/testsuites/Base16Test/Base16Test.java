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

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Base16}.
 */
class Base16Test {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /** A sample plain-text message reused across several encode/decode tests. */
    private static final String HELLO_WORLD = "Hello World";

    /** The upper-case Base16 encoding of {@link #HELLO_WORLD}. */
    private static final String HELLO_WORLD_BASE16 = "48656C6C6F20576F726C64";

    /** Source of randomness for the round-trip tests. */
    private final Random random = new Random();

    /**
     * @return the random.
     */
    Random getRandom() {
        return this.random;
    }

    /**
     * Tests a single upper-case encode/decode round-trip of "Hello World".
     */
    @Test
    void testBase16() {
        final byte[] encodedBytes = new Base16().encode(StringUtils.getBytesUtf8(HELLO_WORLD));
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);
        assertEquals(HELLO_WORLD_BASE16, encodedContent, "encoding hello world");

        final byte[] decodedBytes = new Base16().decode(encodedBytes);
        final String decodedContent = StringUtils.newStringUtf8(decodedBytes);
        assertEquals(HELLO_WORLD, decodedContent, "decoding hello world");
    }

    @Test
    void testBase16AtBufferEnd() {
        // Padding sits before the payload, leaving the payload flush against the buffer's end.
        assertEncodesHelloWorldWithinPaddedBuffer(100, 0);
    }

    @Test
    void testBase16AtBufferMiddle() {
        // Padding surrounds the payload on both sides.
        assertEncodesHelloWorldWithinPaddedBuffer(100, 100);
    }

    @Test
    void testBase16AtBufferStart() {
        // Padding sits after the payload, leaving the payload flush against the buffer's start.
        assertEncodesHelloWorldWithinPaddedBuffer(0, 100);
    }

    /**
     * Encodes "Hello World" when it is embedded inside a larger buffer, padded with zero bytes before and after, and
     * asserts that only the payload region is encoded.
     *
     * @param startPadSize number of zero bytes prepended before the payload.
     * @param endPadSize   number of zero bytes appended after the payload.
     */
    private void assertEncodesHelloWorldWithinPaddedBuffer(final int startPadSize, final int endPadSize) {
        final byte[] payload = StringUtils.getBytesUtf8(HELLO_WORLD);
        byte[] buffer = ArrayUtils.addAll(payload, new byte[endPadSize]);
        buffer = ArrayUtils.addAll(new byte[startPadSize], buffer);
        final byte[] encodedBytes = new Base16().encode(buffer, startPadSize, payload.length);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);
        assertEquals(HELLO_WORLD_BASE16, encodedContent, "encoding hello world");
    }

    /**
     * Verifies {@code encodeToString} (and the static encode) for a populated array, an empty array, and {@code null}.
     */
    @Test
    void testByteToStringVariations() {
        final Base16 base16 = new Base16();
        final byte[] helloWorld = StringUtils.getBytesUtf8(HELLO_WORLD);
        final byte[] empty = {};
        final byte[] nullBytes = null;

        assertEquals(HELLO_WORLD_BASE16, base16.encodeToString(helloWorld), "byteToString Hello World");
        assertEquals(HELLO_WORLD_BASE16, StringUtils.newStringUtf8(new Base16().encode(helloWorld)), "byteToString static Hello World");
        assertEquals("", base16.encodeToString(empty), "byteToString \"\"");
        assertEquals("", StringUtils.newStringUtf8(new Base16().encode(empty)), "byteToString static \"\"");
        assertNull(base16.encodeToString(nullBytes), "byteToString null");
        assertNull(StringUtils.newStringUtf8(new Base16().encode(nullBytes)), "byteToString static null");
    }

    /**
     * An encode length that overflows the internal buffer size must be rejected.
     */
    @Test
    void testCheckEncodeLengthBounds() {
        final Base16 base16 = new Base16();
        assertThrows(IllegalArgumentException.class, () -> base16.encode(new byte[10], 0, 1 << 30));
    }

    /**
     * isBase16 throws RuntimeException on some non-Base16 bytes
     */
    @Test
    void testCodec68() {
        final byte[] nonBase16Bytes = { 'n', 'H', '=', '=', (byte) 0x9c };
        final Base16 base16 = new Base16();
        assertThrows(RuntimeException.class, () -> base16.decode(nonBase16Bytes));
    }

    /**
     * The lower-case constructor encodes using the lower-case alphabet.
     */
    @Test
    void testConstructor_LowerCase() {
        final Base16 base16 = new Base16(true);
        final byte[] encoded = base16.encode(BaseNTestData.DECODED);
        final String expectedResult = Base16TestData.ENCODED_UTF8_LOWERCASE;
        final String result = StringUtils.newStringUtf8(encoded);
        assertEquals(expectedResult, result, "new Base16(true)");
    }

    /**
     * The upper-case constructor (with a strict decoding policy) still encodes using the upper-case alphabet.
     */
    @Test
    void testConstructor_LowerCase_DecodingPolicy() {
        final Base16 base16 = new Base16(false, CodecPolicy.STRICT);
        final byte[] encoded = base16.encode(BaseNTestData.DECODED);
        final String expectedResult = Base16TestData.ENCODED_UTF8_UPPERCASE;
        final String result = StringUtils.newStringUtf8(encoded);
        assertEquals(result, expectedResult, "new base16(false, CodecPolicy.STRICT)");
    }

    /**
     * Every public constructor variant can be instantiated without error.
     */
    @Test
    void testConstructors() {
        new Base16();
        new Base16(false);
        new Base16(true);
        new Base16(false, CodecPolicy.LENIENT);
        new Base16(false, CodecPolicy.STRICT);
    }

    /**
     * Feeding the decoder one or a few characters at a time, across several calls sharing one context, still produces
     * the full decoded message.
     */
    @Test
    void testDecodeSingleBytes() {
        final String encoded = "556E74696C206E6578742074696D6521";

        final BaseNCodec.Context context = new BaseNCodec.Context();
        final Base16 base16 = new Base16();

        final byte[] encodedBytes = StringUtils.getBytesUtf8(encoded);

        // decode byte-by-byte
        base16.decode(encodedBytes, 0, 1, context);
        base16.decode(encodedBytes, 1, 1, context); // yields "U"
        base16.decode(encodedBytes, 2, 1, context);
        base16.decode(encodedBytes, 3, 1, context); // yields "n"

        // decode split hex-pairs
        base16.decode(encodedBytes, 4, 3, context); // yields "t"
        base16.decode(encodedBytes, 7, 3, context); // yields "il"
        base16.decode(encodedBytes, 10, 3, context); // yields " "

        // decode remaining
        base16.decode(encodedBytes, 13, 19, context); // yields "next time!"

        final String decoded = readDecodedString(context);

        assertEquals("Until next time!", decoded);
    }

    /**
     * The single-byte decode fast-path stashes the half-byte in {@code ibitWorkArea} until the pairing character
     * arrives, only then writing the completed byte to the buffer.
     */
    @Test
    void testDecodeSingleBytesOptimization() {
        final BaseNCodec.Context context = new BaseNCodec.Context();
        assertEquals(0, context.ibitWorkArea);
        assertNull(context.buffer);

        final byte[] data = new byte[1];

        final Base16 base16 = new Base16();

        // First nibble 'E' (= 0xE) is held in the work area; no byte emitted yet.
        data[0] = (byte) 'E';
        base16.decode(data, 0, 1, context);
        assertEquals(15, context.ibitWorkArea);
        assertNull(context.buffer);

        // Second nibble 'F' completes the pair; the work area resets and 0xEF is emitted.
        data[0] = (byte) 'F';
        base16.decode(data, 0, 1, context);
        assertEquals(0, context.ibitWorkArea);

        assertEquals((byte) 0xEF, context.buffer[0]);
    }

    /**
     * Encoding and decoding of an empty byte array (and {@code null}) produces empty/null results.
     */
    @Test
    void testEmptyBase16() {
        byte[] empty = {};
        byte[] result = new Base16().encode(empty);
        assertEquals(0, result.length, "empty Base16 encode");
        assertNull(new Base16().encode(null), "empty Base16 encode");
        result = new Base16().encode(empty, 0, 1);
        assertEquals(0, result.length, "empty Base16 encode with offset");
        assertNull(new Base16().encode(null), "empty Base16 encode with offset");

        empty = new byte[0];
        result = new Base16().decode(empty);
        assertEquals(0, result.length, "empty Base16 decode");
        assertNull(new Base16().decode((byte[]) null), "empty Base16 encode");
    }

    /**
     * Encode then decode several large random arrays and confirm the round-trip is lossless.
     */
    @Test
    void testEncodeDecodeRandom() {
        for (int i = 1; i < 5; i++) {
            final int len = getRandom().nextInt(10000) + 1;
            final byte[] data = new byte[len];
            getRandom().nextBytes(data);
            final byte[] encoded = new Base16().encode(data);
            final byte[] decoded = new Base16().decode(encoded);
            assertArrayEquals(data, decoded);
        }
    }

    /**
     * Encode then decode random arrays of every length from 0 to 11 and confirm the round-trip is lossless.
     */
    @Test
    void testEncodeDecodeSmall() {
        for (int i = 0; i < 12; i++) {
            final byte[] data = new byte[i];
            getRandom().nextBytes(data);
            final byte[] encoded = new Base16().encode(data);
            final byte[] decoded = new Base16().decode(encoded);
            assertArrayEquals(data, decoded, toCsv(data) + " equals " + toCsv(decoded));
        }
    }

    /**
     * {@code isInAlphabet} accepts only the digits and the six letters of the configured (lower- or upper-) case
     * alphabet, and rejects everything else including out-of-range bytes.
     */
    @Test
    void testIsInAlphabet() {
        // invalid bounds
        Base16 base16 = Base16.builder().setLowerCase(true).get();
        assertFalse(base16.isInAlphabet((byte) 0));
        assertFalse(base16.isInAlphabet((byte) 1));
        assertFalse(base16.isInAlphabet((byte) -1));
        assertFalse(base16.isInAlphabet((byte) -15));
        assertFalse(base16.isInAlphabet((byte) -16));
        assertFalse(base16.isInAlphabet((byte) 128));
        assertFalse(base16.isInAlphabet((byte) 255));
        // lower-case: '0'-'9' and 'a'-'f' are valid; 'A'-'F' are not
        base16 = new Base16(true);
        for (char c = '0'; c <= '9'; c++) {
            assertTrue(base16.isInAlphabet((byte) c));
        }
        for (char c = 'a'; c <= 'f'; c++) {
            assertTrue(base16.isInAlphabet((byte) c));
        }
        for (char c = 'A'; c <= 'F'; c++) {
            assertFalse(base16.isInAlphabet((byte) c));
        }
        assertFalse(base16.isInAlphabet((byte) ('0' - 1)));
        assertFalse(base16.isInAlphabet((byte) ('9' + 1)));
        assertFalse(base16.isInAlphabet((byte) ('a' - 1)));
        assertFalse(base16.isInAlphabet((byte) ('f' + 1)));
        assertFalse(base16.isInAlphabet((byte) ('z' + 1)));
        // upper-case: '0'-'9' and 'A'-'F' are valid; 'a'-'f' are not
        base16 = new Base16(false);
        for (char c = '0'; c <= '9'; c++) {
            assertTrue(base16.isInAlphabet((byte) c));
        }
        for (char c = 'a'; c <= 'f'; c++) {
            assertFalse(base16.isInAlphabet((byte) c));
        }
        for (char c = 'A'; c <= 'F'; c++) {
            assertTrue(base16.isInAlphabet((byte) c));
        }
        assertFalse(base16.isInAlphabet((byte) ('0' - 1)));
        assertFalse(base16.isInAlphabet((byte) ('9' + 1)));
        assertFalse(base16.isInAlphabet((byte) ('A' - 1)));
        assertFalse(base16.isInAlphabet((byte) ('F' + 1)));
        assertFalse(base16.isInAlphabet((byte) ('Z' + 1)));
    }

    /**
     * Decodes a set of known lower-case Base16 strings back to their original text.
     */
    @Test
    void testKnownDecodings() {
        assertEquals("The quick brown fox jumped over the lazy dogs.", new String(new Base16(true)
                .decode("54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e".getBytes(CHARSET_UTF8))));
        assertEquals("It was the best of times, it was the worst of times.", new String(new Base16(true)
                .decode("497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e".getBytes(CHARSET_UTF8))));
        assertEquals("http://jakarta.apache.org/commmons",
                new String(new Base16(true).decode("687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73".getBytes(CHARSET_UTF8))));
        assertEquals("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz", new String(new Base16(true)
                .decode("4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a".getBytes(CHARSET_UTF8))));
        assertEquals("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }",
                new String(new Base16(true).decode("7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d".getBytes(CHARSET_UTF8))));
        assertEquals("xyzzy!", new String(new Base16(true).decode("78797a7a7921".getBytes(CHARSET_UTF8))));
    }

    /**
     * Encodes a set of known strings and checks them against their expected lower-case Base16 output.
     */
    @Test
    void testKnownEncodings() {
        assertEquals("54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e",
                new String(new Base16(true).encode("The quick brown fox jumped over the lazy dogs.".getBytes(CHARSET_UTF8))));
        assertEquals("497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e",
                new String(new Base16(true).encode("It was the best of times, it was the worst of times.".getBytes(CHARSET_UTF8))));
        assertEquals("687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73",
                new String(new Base16(true).encode("http://jakarta.apache.org/commmons".getBytes(CHARSET_UTF8))));
        assertEquals("4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a",
                new String(new Base16(true).encode("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz".getBytes(CHARSET_UTF8))));
        assertEquals("7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d",
                new String(new Base16(true).encode("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }".getBytes(CHARSET_UTF8))));
        assertEquals("78797a7a7921", new String(new Base16(true).encode("xyzzy!".getBytes(CHARSET_UTF8))));
    }

    /**
     * Lenient decoding silently drops a dangling final character that cannot form a complete hex-pair.
     */
    @Test
    void testLenientDecoding() {
        final String encoded = "aabbccdde"; // Note the trailing `e` which does not make up a hex-pair and so is only 1/2 byte

        final Base16 base16 = new Base16(true, CodecPolicy.LENIENT);
        assertEquals(CodecPolicy.LENIENT, base16.getCodecPolicy());

        final byte[] decoded = base16.decode(StringUtils.getBytesUtf8(encoded));
        assertArrayEquals(new byte[] { (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd }, decoded);
    }

    /**
     * Each character outside the Base16 alphabet must cause decoding to fail.
     */
    @Test
    void testNonBase16Test() {
        final byte[] invalidEncodedChars = { '/', ':', '@', 'G', '%', '`', 'g' };

        final byte[] encoded = new byte[1];
        for (final byte invalidEncodedChar : invalidEncodedChars) {
            encoded[0] = invalidEncodedChar;
            assertThrows(IllegalArgumentException.class, () -> new Base16().decode(encoded), "Invalid Base16 char: " + (char) invalidEncodedChar);
        }
    }

    /**
     * Decoding an object that is not a byte array or String is rejected with a {@link DecoderException}.
     */
    @Test
    void testObjectDecodeWithInvalidParameter() {
        assertThrows(DecoderException.class, () -> new Base16().decode(Integer.valueOf(5)));
    }

    /**
     * The {@code Object}-based decode accepts a previously encoded byte array and reverses it.
     */
    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object encoded = new Base16().encode(original.getBytes(CHARSET_UTF8));

        final Base16 base16 = new Base16();
        final Object decoded = base16.decode(encoded);
        final byte[] decodedBytes = (byte[]) decoded;
        final String result = new String(decodedBytes);

        assertEquals(original, result, "dest string does not equal original");
    }

    /**
     * The {@code Object}-based encode of a byte array produces the expected Base16 string.
     */
    @Test
    void testObjectEncode() {
        final Base16 base16 = new Base16();
        assertEquals(new String(base16.encode(HELLO_WORLD.getBytes(CHARSET_UTF8))), HELLO_WORLD_BASE16);
    }

    /**
     * Encoding an object that is not a byte array (here a String) is rejected with an {@link EncoderException}.
     */
    @Test
    void testObjectEncodeWithInvalidParameter() {
        assertThrows(EncoderException.class, () -> new Base16().encode("Yadayadayada"));
    }

    /**
     * The {@code Object}-based encode followed by decode round-trips the original bytes.
     */
    @Test
    void testObjectEncodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object originalBytes = original.getBytes(CHARSET_UTF8);

        final Object encoded = new Base16().encode(originalBytes);
        final byte[] decoded = new Base16().decode((byte[]) encoded);
        final String result = new String(decoded);

        assertEquals(original, result, "dest string does not equal original");
    }

    /**
     * Streaming decode with alternating odd/even chunk sizes (across one shared context) reassembles the message.
     */
    @Test
    void testOddEvenDecoding() {
        final String encoded = "4142434445";

        final BaseNCodec.Context context = new BaseNCodec.Context();
        final Base16 base16 = new Base16();

        final byte[] encodedBytes = StringUtils.getBytesUtf8(encoded);

        // pass odd, then even, then odd amount of data
        base16.decode(encodedBytes, 0, 3, context);
        base16.decode(encodedBytes, 3, 4, context);
        base16.decode(encodedBytes, 7, 3, context);

        final String decoded = readDecodedString(context);

        assertEquals("ABCDE", decoded);
    }

    /**
     * Encoding two-byte inputs yields the expected four-character hex strings, and every {@code (i, i)} byte pair
     * survives an encode/decode round-trip.
     */
    @Test
    void testPairs() {
        assertEquals("0000", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0 })));
        assertEquals("0001", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 1 })));
        assertEquals("0002", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 2 })));
        assertEquals("0003", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 3 })));
        assertEquals("0004", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 4 })));
        assertEquals("0005", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 5 })));
        assertEquals("0006", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 6 })));
        assertEquals("0007", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 7 })));
        assertEquals("0008", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 8 })));
        assertEquals("0009", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 9 })));
        assertEquals("000A", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 10 })));
        assertEquals("000B", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 11 })));
        assertEquals("000C", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 12 })));
        assertEquals("000D", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 13 })));
        assertEquals("000E", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 14 })));
        assertEquals("000F", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 15 })));
        assertEquals("0010", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 16 })));
        assertEquals("0011", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 17 })));
        for (int i = -128; i <= 127; i++) {
            final byte[] test = { (byte) i, (byte) i };
            assertArrayEquals(test, new Base16().decode(new Base16().encode(test)));
        }
    }

    /**
     * Encoding single-byte inputs yields the expected two-character hex strings, and every single byte survives an
     * encode/decode round-trip.
     */
    @Test
    void testSingletons() {
        assertEquals("00", new String(new Base16().encode(new byte[] { (byte) 0 })));
        assertEquals("01", new String(new Base16().encode(new byte[] { (byte) 1 })));
        assertEquals("02", new String(new Base16().encode(new byte[] { (byte) 2 })));
        assertEquals("03", new String(new Base16().encode(new byte[] { (byte) 3 })));
        assertEquals("04", new String(new Base16().encode(new byte[] { (byte) 4 })));
        assertEquals("05", new String(new Base16().encode(new byte[] { (byte) 5 })));
        assertEquals("06", new String(new Base16().encode(new byte[] { (byte) 6 })));
        assertEquals("07", new String(new Base16().encode(new byte[] { (byte) 7 })));
        assertEquals("08", new String(new Base16().encode(new byte[] { (byte) 8 })));
        assertEquals("09", new String(new Base16().encode(new byte[] { (byte) 9 })));
        assertEquals("0A", new String(new Base16().encode(new byte[] { (byte) 10 })));
        assertEquals("0B", new String(new Base16().encode(new byte[] { (byte) 11 })));
        assertEquals("0C", new String(new Base16().encode(new byte[] { (byte) 12 })));
        assertEquals("0D", new String(new Base16().encode(new byte[] { (byte) 13 })));
        assertEquals("0E", new String(new Base16().encode(new byte[] { (byte) 14 })));
        assertEquals("0F", new String(new Base16().encode(new byte[] { (byte) 15 })));
        assertEquals("10", new String(new Base16().encode(new byte[] { (byte) 16 })));
        assertEquals("11", new String(new Base16().encode(new byte[] { (byte) 17 })));
        assertEquals("12", new String(new Base16().encode(new byte[] { (byte) 18 })));
        assertEquals("13", new String(new Base16().encode(new byte[] { (byte) 19 })));
        assertEquals("14", new String(new Base16().encode(new byte[] { (byte) 20 })));
        assertEquals("15", new String(new Base16().encode(new byte[] { (byte) 21 })));
        assertEquals("16", new String(new Base16().encode(new byte[] { (byte) 22 })));
        assertEquals("17", new String(new Base16().encode(new byte[] { (byte) 23 })));
        assertEquals("18", new String(new Base16().encode(new byte[] { (byte) 24 })));
        assertEquals("19", new String(new Base16().encode(new byte[] { (byte) 25 })));
        assertEquals("1A", new String(new Base16().encode(new byte[] { (byte) 26 })));
        assertEquals("1B", new String(new Base16().encode(new byte[] { (byte) 27 })));
        assertEquals("1C", new String(new Base16().encode(new byte[] { (byte) 28 })));
        assertEquals("1D", new String(new Base16().encode(new byte[] { (byte) 29 })));
        assertEquals("1E", new String(new Base16().encode(new byte[] { (byte) 30 })));
        assertEquals("1F", new String(new Base16().encode(new byte[] { (byte) 31 })));
        assertEquals("20", new String(new Base16().encode(new byte[] { (byte) 32 })));
        assertEquals("21", new String(new Base16().encode(new byte[] { (byte) 33 })));
        assertEquals("22", new String(new Base16().encode(new byte[] { (byte) 34 })));
        assertEquals("23", new String(new Base16().encode(new byte[] { (byte) 35 })));
        assertEquals("24", new String(new Base16().encode(new byte[] { (byte) 36 })));
        assertEquals("25", new String(new Base16().encode(new byte[] { (byte) 37 })));
        assertEquals("26", new String(new Base16().encode(new byte[] { (byte) 38 })));
        assertEquals("27", new String(new Base16().encode(new byte[] { (byte) 39 })));
        assertEquals("28", new String(new Base16().encode(new byte[] { (byte) 40 })));
        assertEquals("29", new String(new Base16().encode(new byte[] { (byte) 41 })));
        assertEquals("2A", new String(new Base16().encode(new byte[] { (byte) 42 })));
        assertEquals("2B", new String(new Base16().encode(new byte[] { (byte) 43 })));
        assertEquals("2C", new String(new Base16().encode(new byte[] { (byte) 44 })));
        assertEquals("2D", new String(new Base16().encode(new byte[] { (byte) 45 })));
        assertEquals("2E", new String(new Base16().encode(new byte[] { (byte) 46 })));
        assertEquals("2F", new String(new Base16().encode(new byte[] { (byte) 47 })));
        assertEquals("30", new String(new Base16().encode(new byte[] { (byte) 48 })));
        assertEquals("31", new String(new Base16().encode(new byte[] { (byte) 49 })));
        assertEquals("32", new String(new Base16().encode(new byte[] { (byte) 50 })));
        assertEquals("33", new String(new Base16().encode(new byte[] { (byte) 51 })));
        assertEquals("34", new String(new Base16().encode(new byte[] { (byte) 52 })));
        assertEquals("35", new String(new Base16().encode(new byte[] { (byte) 53 })));
        assertEquals("36", new String(new Base16().encode(new byte[] { (byte) 54 })));
        assertEquals("37", new String(new Base16().encode(new byte[] { (byte) 55 })));
        assertEquals("38", new String(new Base16().encode(new byte[] { (byte) 56 })));
        assertEquals("39", new String(new Base16().encode(new byte[] { (byte) 57 })));
        assertEquals("3A", new String(new Base16().encode(new byte[] { (byte) 58 })));
        assertEquals("3B", new String(new Base16().encode(new byte[] { (byte) 59 })));
        assertEquals("3C", new String(new Base16().encode(new byte[] { (byte) 60 })));
        assertEquals("3D", new String(new Base16().encode(new byte[] { (byte) 61 })));
        assertEquals("3E", new String(new Base16().encode(new byte[] { (byte) 62 })));
        assertEquals("3F", new String(new Base16().encode(new byte[] { (byte) 63 })));
        assertEquals("40", new String(new Base16().encode(new byte[] { (byte) 64 })));
        assertEquals("41", new String(new Base16().encode(new byte[] { (byte) 65 })));
        assertEquals("42", new String(new Base16().encode(new byte[] { (byte) 66 })));
        assertEquals("43", new String(new Base16().encode(new byte[] { (byte) 67 })));
        assertEquals("44", new String(new Base16().encode(new byte[] { (byte) 68 })));
        assertEquals("45", new String(new Base16().encode(new byte[] { (byte) 69 })));
        assertEquals("46", new String(new Base16().encode(new byte[] { (byte) 70 })));
        assertEquals("47", new String(new Base16().encode(new byte[] { (byte) 71 })));
        assertEquals("48", new String(new Base16().encode(new byte[] { (byte) 72 })));
        assertEquals("49", new String(new Base16().encode(new byte[] { (byte) 73 })));
        assertEquals("4A", new String(new Base16().encode(new byte[] { (byte) 74 })));
        assertEquals("4B", new String(new Base16().encode(new byte[] { (byte) 75 })));
        assertEquals("4C", new String(new Base16().encode(new byte[] { (byte) 76 })));
        assertEquals("4D", new String(new Base16().encode(new byte[] { (byte) 77 })));
        assertEquals("4E", new String(new Base16().encode(new byte[] { (byte) 78 })));
        assertEquals("4F", new String(new Base16().encode(new byte[] { (byte) 79 })));
        assertEquals("50", new String(new Base16().encode(new byte[] { (byte) 80 })));
        assertEquals("51", new String(new Base16().encode(new byte[] { (byte) 81 })));
        assertEquals("52", new String(new Base16().encode(new byte[] { (byte) 82 })));
        assertEquals("53", new String(new Base16().encode(new byte[] { (byte) 83 })));
        assertEquals("54", new String(new Base16().encode(new byte[] { (byte) 84 })));
        assertEquals("55", new String(new Base16().encode(new byte[] { (byte) 85 })));
        assertEquals("56", new String(new Base16().encode(new byte[] { (byte) 86 })));
        assertEquals("57", new String(new Base16().encode(new byte[] { (byte) 87 })));
        assertEquals("58", new String(new Base16().encode(new byte[] { (byte) 88 })));
        assertEquals("59", new String(new Base16().encode(new byte[] { (byte) 89 })));
        assertEquals("5A", new String(new Base16().encode(new byte[] { (byte) 90 })));
        assertEquals("5B", new String(new Base16().encode(new byte[] { (byte) 91 })));
        assertEquals("5C", new String(new Base16().encode(new byte[] { (byte) 92 })));
        assertEquals("5D", new String(new Base16().encode(new byte[] { (byte) 93 })));
        assertEquals("5E", new String(new Base16().encode(new byte[] { (byte) 94 })));
        assertEquals("5F", new String(new Base16().encode(new byte[] { (byte) 95 })));
        assertEquals("60", new String(new Base16().encode(new byte[] { (byte) 96 })));
        assertEquals("61", new String(new Base16().encode(new byte[] { (byte) 97 })));
        assertEquals("62", new String(new Base16().encode(new byte[] { (byte) 98 })));
        assertEquals("63", new String(new Base16().encode(new byte[] { (byte) 99 })));
        assertEquals("64", new String(new Base16().encode(new byte[] { (byte) 100 })));
        assertEquals("65", new String(new Base16().encode(new byte[] { (byte) 101 })));
        assertEquals("66", new String(new Base16().encode(new byte[] { (byte) 102 })));
        assertEquals("67", new String(new Base16().encode(new byte[] { (byte) 103 })));
        assertEquals("68", new String(new Base16().encode(new byte[] { (byte) 104 })));
        for (int i = -128; i <= 127; i++) {
            final byte[] test = { (byte) i };
            assertArrayEquals(test, new Base16().decode(new Base16().encode(test)));
        }
    }

    /**
     * Strict decoding rejects a dangling final character that cannot form a complete hex-pair.
     */
    @Test
    void testStrictDecoding() {
        final String encoded = "aabbccdde"; // Note the trailing `e` which does not make up a hex-pair and so is only 1/2 byte

        final Base16 base16 = new Base16(true, CodecPolicy.STRICT);
        assertEquals(CodecPolicy.STRICT, base16.getCodecPolicy());
        assertThrows(IllegalArgumentException.class, () -> base16.decode(StringUtils.getBytesUtf8(encoded)));
    }

    /**
     * Verifies decoding via the String, Object, and static entry points for a populated string, an empty string, and
     * {@code null}.
     */
    @Test
    void testStringToByteVariations() throws DecoderException {
        final Base16 base16 = new Base16();
        final String helloWorldEncoded = HELLO_WORLD_BASE16;
        final String emptyEncoded = "";
        final String nullEncoded = null;

        assertEquals(HELLO_WORLD, StringUtils.newStringUtf8(base16.decode(helloWorldEncoded)), "StringToByte Hello World");
        assertEquals(HELLO_WORLD, StringUtils.newStringUtf8((byte[]) new Base16().decode((Object) helloWorldEncoded)), "StringToByte Hello World");
        assertEquals(HELLO_WORLD, StringUtils.newStringUtf8(new Base16().decode(helloWorldEncoded)), "StringToByte static Hello World");
        assertEquals("", StringUtils.newStringUtf8(new Base16().decode(emptyEncoded)), "StringToByte \"\"");
        assertEquals("", StringUtils.newStringUtf8(new Base16().decode(emptyEncoded)), "StringToByte static \"\"");
        assertNull(StringUtils.newStringUtf8(new Base16().decode(nullEncoded)), "StringToByte null");
        assertNull(StringUtils.newStringUtf8(new Base16().decode(nullEncoded)), "StringToByte static null");
    }

    /**
     * Encoding three-byte inputs yields the expected six-character hex strings.
     */
    @Test
    void testTriplets() {
        assertEquals("000000", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 0 })));
        assertEquals("000001", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 1 })));
        assertEquals("000002", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 2 })));
        assertEquals("000003", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 3 })));
        assertEquals("000004", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 4 })));
        assertEquals("000005", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 5 })));
        assertEquals("000006", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 6 })));
        assertEquals("000007", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 7 })));
        assertEquals("000008", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 8 })));
        assertEquals("000009", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 9 })));
        assertEquals("00000A", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 10 })));
        assertEquals("00000B", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 11 })));
        assertEquals("00000C", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 12 })));
        assertEquals("00000D", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 13 })));
        assertEquals("00000E", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 14 })));
        assertEquals("00000F", new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) 15 })));
    }

    /**
     * Copies the bytes decoded into the given context out of its internal buffer and returns them as a UTF-8 string.
     */
    private String readDecodedString(final BaseNCodec.Context context) {
        final byte[] decodedBytes = new byte[context.pos];
        System.arraycopy(context.buffer, context.readPos, decodedBytes, 0, decodedBytes.length);
        return StringUtils.newStringUtf8(decodedBytes);
    }

    /**
     * Renders a byte array as a comma-separated list of its (signed) values, used to build readable assertion
     * messages.
     */
    private String toCsv(final byte[] data) {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            buf.append(data[i]);
            if (i != data.length - 1) {
                buf.append(",");
            }
        }
        return buf.toString();
    }
}
