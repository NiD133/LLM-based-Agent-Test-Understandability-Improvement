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
    private static final String HELLO_WORLD = "Hello World";
    private static final String HELLO_WORLD_BASE16 = "48656C6C6F20576F726C64";
    private static final byte[] EMPTY_BYTES = {};

    private static final String[][] LOWER_CASE_EXAMPLES = {
        {
            "The quick brown fox jumped over the lazy dogs.",
            "54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e"
        },
        {
            "It was the best of times, it was the worst of times.",
            "497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e"
        },
        {
            "http://jakarta.apache.org/commmons",
            "687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73"
        },
        {
            "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz",
            "4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a"
        },
        {
            "{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }",
            "7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d"
        },
        {
            "xyzzy!",
            "78797a7a7921"
        }
    };

    private final Random random = new Random();

    /**
     * @return the random.
     */
    Random getRandom() {
        return this.random;
    }

    /**
     * Test the Base16 implementation.
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
        testBase16InBuffer(100, 0);
    }

    @Test
    void testBase16AtBufferMiddle() {
        testBase16InBuffer(100, 100);
    }

    @Test
    void testBase16AtBufferStart() {
        testBase16InBuffer(0, 100);
    }

    private void testBase16InBuffer(final int startPadSize, final int endPadSize) {
        final byte[] bytesUtf8 = StringUtils.getBytesUtf8(HELLO_WORLD);
        byte[] buffer = ArrayUtils.addAll(bytesUtf8, new byte[endPadSize]);
        buffer = ArrayUtils.addAll(new byte[startPadSize], buffer);

        final byte[] encodedBytes = new Base16().encode(buffer, startPadSize, bytesUtf8.length);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);
        assertEquals(HELLO_WORLD_BASE16, encodedContent, "encoding hello world");
    }

    @Test
    void testByteToStringVariations() {
        final Base16 base16 = new Base16();
        final byte[] b1 = StringUtils.getBytesUtf8(HELLO_WORLD);
        final byte[] b2 = {};
        final byte[] b3 = null;

        assertEquals(HELLO_WORLD_BASE16, base16.encodeToString(b1), "byteToString Hello World");
        assertEquals(HELLO_WORLD_BASE16, StringUtils.newStringUtf8(new Base16().encode(b1)), "byteToString static Hello World");
        assertEquals("", base16.encodeToString(b2), "byteToString \"\"");
        assertEquals("", StringUtils.newStringUtf8(new Base16().encode(b2)), "byteToString static \"\"");
        assertNull(base16.encodeToString(b3), "byteToString null");
        assertNull(StringUtils.newStringUtf8(new Base16().encode(b3)), "byteToString static null");
    }

    @Test
    void testCheckEncodeLengthBounds() {
        final Base16 base16 = new Base16();
        assertThrows(IllegalArgumentException.class, () -> base16.encode(new byte[10], 0, 1 << 30));
    }

    /**
     * isBase16 throws RuntimeException on some non-Base16 bytes.
     */
    @Test
    void testCodec68() {
        final byte[] x = { 'n', 'H', '=', '=', (byte) 0x9c };
        final Base16 b16 = new Base16();
        assertThrows(RuntimeException.class, () -> b16.decode(x));
    }

    @Test
    void testConstructor_LowerCase() {
        final Base16 base16 = new Base16(true);
        final byte[] encoded = base16.encode(BaseNTestData.DECODED);
        final String expectedResult = Base16TestData.ENCODED_UTF8_LOWERCASE;
        final String result = StringUtils.newStringUtf8(encoded);
        assertEquals(expectedResult, result, "new Base16(true)");
    }

    @Test
    void testConstructor_LowerCase_DecodingPolicy() {
        final Base16 base16 = new Base16(false, CodecPolicy.STRICT);
        final byte[] encoded = base16.encode(BaseNTestData.DECODED);
        final String expectedResult = Base16TestData.ENCODED_UTF8_UPPERCASE;
        final String result = StringUtils.newStringUtf8(encoded);
        assertEquals(result, expectedResult, "new base16(false, CodecPolicy.STRICT)");
    }

    @Test
    void testConstructors() {
        new Base16();
        new Base16(false);
        new Base16(true);
        new Base16(false, CodecPolicy.LENIENT);
        new Base16(false, CodecPolicy.STRICT);
    }

    @Test
    void testDecodeSingleBytes() {
        final String encoded = "556E74696C206E6578742074696D6521";
        final BaseNCodec.Context context = new BaseNCodec.Context();
        final Base16 b16 = new Base16();
        final byte[] encodedBytes = StringUtils.getBytesUtf8(encoded);

        b16.decode(encodedBytes, 0, 1, context);
        b16.decode(encodedBytes, 1, 1, context);
        b16.decode(encodedBytes, 2, 1, context);
        b16.decode(encodedBytes, 3, 1, context);
        b16.decode(encodedBytes, 4, 3, context);
        b16.decode(encodedBytes, 7, 3, context);
        b16.decode(encodedBytes, 10, 3, context);
        b16.decode(encodedBytes, 13, 19, context);

        assertEquals("Until next time!", decodeBufferedContent(context));
    }

    @Test
    void testDecodeSingleBytesOptimization() {
        final BaseNCodec.Context context = new BaseNCodec.Context();
        assertEquals(0, context.ibitWorkArea);
        assertNull(context.buffer);

        final byte[] data = new byte[1];
        final Base16 b16 = new Base16();

        data[0] = (byte) 'E';
        b16.decode(data, 0, 1, context);
        assertEquals(15, context.ibitWorkArea);
        assertNull(context.buffer);

        data[0] = (byte) 'F';
        b16.decode(data, 0, 1, context);
        assertEquals(0, context.ibitWorkArea);
        assertEquals((byte) 0xEF, context.buffer[0]);
    }

    /**
     * Test encode and decode of empty byte array.
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

    @Test
    void testEncodeDecodeRandom() {
        for (int i = 1; i < 5; i++) {
            final int len = getRandom().nextInt(10000) + 1;
            final byte[] data = new byte[len];
            getRandom().nextBytes(data);
            final byte[] enc = new Base16().encode(data);
            final byte[] data2 = new Base16().decode(enc);
            assertArrayEquals(data, data2);
        }
    }

    @Test
    void testEncodeDecodeSmall() {
        for (int i = 0; i < 12; i++) {
            final byte[] data = new byte[i];
            getRandom().nextBytes(data);
            final byte[] enc = new Base16().encode(data);
            final byte[] data2 = new Base16().decode(enc);
            assertArrayEquals(data, data2, toString(data) + " equals " + toString(data2));
        }
    }

    @Test
    void testIsInAlphabet() {
        Base16 b16 = Base16.builder().setLowerCase(true).get();
        assertFalse(b16.isInAlphabet((byte) 0));
        assertFalse(b16.isInAlphabet((byte) 1));
        assertFalse(b16.isInAlphabet((byte) -1));
        assertFalse(b16.isInAlphabet((byte) -15));
        assertFalse(b16.isInAlphabet((byte) -16));
        assertFalse(b16.isInAlphabet((byte) 128));
        assertFalse(b16.isInAlphabet((byte) 255));

        b16 = new Base16(true);
        assertDigitsAreInAlphabet(b16);
        assertRangeIsInAlphabet(b16, 'a', 'f', true);
        assertRangeIsInAlphabet(b16, 'A', 'F', false);
        assertFalse(b16.isInAlphabet((byte) ('0' - 1)));
        assertFalse(b16.isInAlphabet((byte) ('9' + 1)));
        assertFalse(b16.isInAlphabet((byte) ('a' - 1)));
        assertFalse(b16.isInAlphabet((byte) ('f' + 1)));
        assertFalse(b16.isInAlphabet((byte) ('z' + 1)));

        b16 = new Base16(false);
        assertDigitsAreInAlphabet(b16);
        assertRangeIsInAlphabet(b16, 'a', 'f', false);
        assertRangeIsInAlphabet(b16, 'A', 'F', true);
        assertFalse(b16.isInAlphabet((byte) ('0' - 1)));
        assertFalse(b16.isInAlphabet((byte) ('9' + 1)));
        assertFalse(b16.isInAlphabet((byte) ('A' - 1)));
        assertFalse(b16.isInAlphabet((byte) ('F' + 1)));
        assertFalse(b16.isInAlphabet((byte) ('Z' + 1)));
    }

    @Test
    void testKnownDecodings() {
        for (final String[] example : LOWER_CASE_EXAMPLES) {
            assertEquals(example[0], new String(new Base16(true).decode(example[1].getBytes(CHARSET_UTF8))));
        }
    }

    @Test
    void testKnownEncodings() {
        for (final String[] example : LOWER_CASE_EXAMPLES) {
            assertEquals(example[1], new String(new Base16(true).encode(example[0].getBytes(CHARSET_UTF8))));
        }
    }

    @Test
    void testLenientDecoding() {
        final String encoded = "aabbccdde";
        final Base16 b16 = new Base16(true, CodecPolicy.LENIENT);
        assertEquals(CodecPolicy.LENIENT, b16.getCodecPolicy());

        final byte[] decoded = b16.decode(StringUtils.getBytesUtf8(encoded));
        assertArrayEquals(new byte[] { (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd }, decoded);
    }

    @Test
    void testNonBase16Test() {
        final byte[] invalidEncodedChars = { '/', ':', '@', 'G', '%', '`', 'g' };
        final byte[] encoded = new byte[1];
        for (final byte invalidEncodedChar : invalidEncodedChars) {
            encoded[0] = invalidEncodedChar;
            assertThrows(IllegalArgumentException.class, () -> new Base16().decode(encoded), "Invalid Base16 char: " + (char) invalidEncodedChar);
        }
    }

    @Test
    void testObjectDecodeWithInvalidParameter() {
        assertThrows(DecoderException.class, () -> new Base16().decode(Integer.valueOf(5)));
    }

    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object o = new Base16().encode(original.getBytes(CHARSET_UTF8));

        final Base16 b16 = new Base16();
        final Object oDecoded = b16.decode(o);
        final byte[] baDecoded = (byte[]) oDecoded;
        final String dest = new String(baDecoded);

        assertEquals(original, dest, "dest string does not equal original");
    }

    @Test
    void testObjectEncode() {
        final Base16 b16 = new Base16();
        assertEquals(new String(b16.encode(HELLO_WORLD.getBytes(CHARSET_UTF8))), HELLO_WORLD_BASE16);
    }

    @Test
    void testObjectEncodeWithInvalidParameter() {
        assertThrows(EncoderException.class, () -> new Base16().encode("Yadayadayada"));
    }

    @Test
    void testObjectEncodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object origObj = original.getBytes(CHARSET_UTF8);

        final Object oEncoded = new Base16().encode(origObj);
        final byte[] bArray = new Base16().decode((byte[]) oEncoded);
        final String dest = new String(bArray);

        assertEquals(original, dest, "dest string does not equal original");
    }

    @Test
    void testOddEvenDecoding() {
        final String encoded = "4142434445";
        final BaseNCodec.Context context = new BaseNCodec.Context();
        final Base16 base16 = new Base16();
        final byte[] encodedBytes = StringUtils.getBytesUtf8(encoded);

        base16.decode(encodedBytes, 0, 3, context);
        base16.decode(encodedBytes, 3, 4, context);
        base16.decode(encodedBytes, 7, 3, context);

        assertEquals("ABCDE", decodeBufferedContent(context));
    }

    @Test
    void testPairs() {
        for (int i = 0; i <= 17; i++) {
            assertEquals(String.format("00%02X", i), new String(new Base16().encode(new byte[] { (byte) 0, (byte) i })));
        }
        for (int i = -128; i <= 127; i++) {
            final byte[] test = { (byte) i, (byte) i };
            assertArrayEquals(test, new Base16().decode(new Base16().encode(test)));
        }
    }

    @Test
    void testSingletons() {
        for (int i = 0; i <= 104; i++) {
            assertEquals(String.format("%02X", i), new String(new Base16().encode(new byte[] { (byte) i })));
        }
        for (int i = -128; i <= 127; i++) {
            final byte[] test = { (byte) i };
            assertArrayEquals(test, new Base16().decode(new Base16().encode(test)));
        }
    }

    @Test
    void testStrictDecoding() {
        final String encoded = "aabbccdde";
        final Base16 b16 = new Base16(true, CodecPolicy.STRICT);
        assertEquals(CodecPolicy.STRICT, b16.getCodecPolicy());
        assertThrows(IllegalArgumentException.class, () -> b16.decode(StringUtils.getBytesUtf8(encoded)));
    }

    @Test
    void testStringToByteVariations() throws DecoderException {
        final Base16 base16 = new Base16();
        final String s1 = HELLO_WORLD_BASE16;
        final String s2 = "";
        final String s3 = null;

        assertEquals(HELLO_WORLD, StringUtils.newStringUtf8(base16.decode(s1)), "StringToByte Hello World");
        assertEquals(HELLO_WORLD, StringUtils.newStringUtf8((byte[]) new Base16().decode((Object) s1)), "StringToByte Hello World");
        assertEquals(HELLO_WORLD, StringUtils.newStringUtf8(new Base16().decode(s1)), "StringToByte static Hello World");
        assertEquals("", StringUtils.newStringUtf8(new Base16().decode(s2)), "StringToByte \"\"");
        assertEquals("", StringUtils.newStringUtf8(new Base16().decode(s2)), "StringToByte static \"\"");
        assertNull(StringUtils.newStringUtf8(new Base16().decode(s3)), "StringToByte null");
        assertNull(StringUtils.newStringUtf8(new Base16().decode(s3)), "StringToByte static null");
    }

    @Test
    void testTriplets() {
        for (int i = 0; i <= 15; i++) {
            assertEquals(String.format("0000%02X", i), new String(new Base16().encode(new byte[] { (byte) 0, (byte) 0, (byte) i })));
        }
    }

    private void assertDigitsAreInAlphabet(final Base16 base16) {
        assertRangeIsInAlphabet(base16, '0', '9', true);
    }

    private void assertRangeIsInAlphabet(final Base16 base16, final char start, final char end, final boolean expected) {
        for (char c = start; c <= end; c++) {
            assertEquals(expected, base16.isInAlphabet((byte) c));
        }
    }

    private String decodeBufferedContent(final BaseNCodec.Context context) {
        final byte[] decodedBytes = new byte[context.pos];
        System.arraycopy(context.buffer, context.readPos, decodedBytes, 0, decodedBytes.length);
        return StringUtils.newStringUtf8(decodedBytes);
    }

    private String toString(final byte[] data) {
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
