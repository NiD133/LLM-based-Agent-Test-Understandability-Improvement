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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.ArrayFill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests {@link Base58}.
 */
public class Base58Test {

    private static final int BOUND = 10_000;

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * Asserts that {@code data} and {@code dec} are equal, including the loop index in the failure
     * message to aid debugging when this is called from within a loop.
     */
    private static void assertArrayEqualsAt(final byte[] data, final byte[] dec, final int index) {
        assertArrayEquals(data, dec, () -> String.format("Failed for length %,d: %s", index, Arrays.toString(data)));
    }

    private final Random random = new Random();

    @Test
    void testBase58() {
        final String input = "Hello World";
        final byte[] encoded = new Base58().encode(StringUtils.getBytesUtf8(input));
        assertEquals("JxF12TrwUP45BMd", StringUtils.newStringUtf8(encoded), "encoding hello world");
        final byte[] decoded = new Base58().decode(encoded);
        assertEquals(input, StringUtils.newStringUtf8(decoded), "decoding hello world");
    }

    @Test
    void testEmptyBase58() {
        // Empty array encodes/decodes to empty array; null input returns null
        final byte[] empty = new byte[0];
        final byte[] encodedEmpty = new Base58().encode(empty);
        assertEquals(0, encodedEmpty.length, "empty Base58 encode");
        assertNull(new Base58().encode(null), "empty Base58 encode");
        final byte[] decodedEmpty = new Base58().decode(empty);
        assertEquals(0, decodedEmpty.length, "empty Base58 decode");
        assertNull(new Base58().decode((byte[]) null), "empty Base58 decode");
    }

    @Test
    void testEncodeDecode() {
        for (int i = 1; i < 5; i++) {
            final byte[] data = new byte[random.nextInt(BOUND) + 1];
            Arrays.fill(data, (byte) i);
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEqualsAt(data, dec, i);
        }
    }

    @Test
    void testEncodeDecodeRandom() {
        for (int i = 1; i < 5; i++) {
            final byte[] data = new byte[random.nextInt(BOUND) + 1];
            random.nextBytes(data);
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEqualsAt(data, dec, i);
        }
    }

    @Test
    void testEncodeDecodeSmall() {
        for (int i = 0; i < 12; i++) {
            final byte[] data = new byte[i];
            Arrays.fill(data, (byte) i);
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEqualsAt(data, dec, i);
        }
    }

    @Test
    void testEncodeDecodeSmallRandom() {
        for (int i = 0; i < 12; i++) {
            final byte[] data = new byte[i];
            random.nextBytes(data);
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEqualsAt(data, dec, i);
        }
    }

    @Test
    void testHexEncoding() {
        final String hexString = "48656c6c6f20576f726c6421";
        final byte[] encoded = new Base58().encode(StringUtils.getBytesUtf8(hexString));
        final byte[] decoded = new Base58().decode(StringUtils.newStringUtf8(encoded));
        assertEquals("5m7UdtXCfQxGvX2K9dLrkNs7AFMS98qn8", StringUtils.newStringUtf8(encoded), "Hex encoding failed");
        assertEquals(hexString, StringUtils.newStringUtf8(decoded), "Hex decoding failed");
    }

    @Test
    void testInvalidCharacters() {
        // '0', 'O', 'I', 'l' are explicitly excluded from the Base58 alphabet to avoid visual ambiguity
        final byte[] invalidChars = "0OIl".getBytes(CHARSET_UTF8);
        assertThrows(IllegalArgumentException.class, () -> new Base58().decode(invalidChars));
    }

    @Test
    void testIsInAlphabet() {
        final Base58 base58 = new Base58();
        // Valid characters: digits 1-9
        for (char c = '1'; c <= '9'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Valid characters: uppercase A-H (alphabet skips 'I')
        for (char c = 'A'; c <= 'H'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Valid characters: uppercase J-N (alphabet skips 'I' and 'O')
        for (char c = 'J'; c <= 'N'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Valid characters: uppercase P-Z (alphabet skips 'O')
        for (char c = 'P'; c <= 'Z'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Valid characters: lowercase a-k (alphabet skips 'l')
        for (char c = 'a'; c <= 'k'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Valid characters: lowercase m-z (alphabet skips 'l')
        for (char c = 'm'; c <= 'z'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Characters excluded from Base58 to avoid visual ambiguity
        assertFalse(base58.isInAlphabet((byte) '0'), "char 0");
        assertFalse(base58.isInAlphabet((byte) 'O'), "char O");
        assertFalse(base58.isInAlphabet((byte) 'I'), "char I");
        assertFalse(base58.isInAlphabet((byte) 'l'), "char l");
        // Out-of-range byte values
        assertFalse(base58.isInAlphabet((byte) -1));
        assertFalse(base58.isInAlphabet((byte) 0));
        assertFalse(base58.isInAlphabet((byte) 128));
        assertFalse(base58.isInAlphabet((byte) 255));
    }

    @Test
    void testLeadingZeros() {
        // Each leading zero byte encodes as the '1' character in Base58
        final byte[] input = { 0, 0, 1, 2, 3 };
        final byte[] encoded = new Base58().encode(input);
        final String encodedStr = new String(encoded);
        assertTrue(encodedStr.startsWith("11"), "Leading zeros should encode as '1' characters");
        final byte[] decoded = new Base58().decode(encoded);
        assertArrayEquals(input, decoded, "Decoded should match original including leading zeros");
    }

    @Test
    void testObjectDecodeWithInvalidParameter() {
        assertThrows(DecoderException.class, () -> new Base58().decode(Integer.valueOf(5)));
    }

    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object encodedObject = new Base58().encode(original.getBytes(CHARSET_UTF8));
        final Base58 base58 = new Base58();
        final Object decodedObject = base58.decode(encodedObject);
        final byte[] decodedBytes = (byte[]) decodedObject;
        final String dest = new String(decodedBytes);
        assertEquals(original, dest, "dest string does not equal original");
    }

    @Test
    void testObjectEncodeWithInvalidParameter() {
        assertThrows(EncoderException.class, () -> new Base58().encode("Yadayadayada"));
    }

    @Test
    void testObjectEncodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object inputBytes = original.getBytes(CHARSET_UTF8);
        final Object encodedObject = new Base58().encode(inputBytes);
        final byte[] bArray = new Base58().decode((byte[]) encodedObject);
        final String dest = new String(bArray);
        assertEquals(original, dest, "dest string does not equal original");
    }

    @Test
    void testRoundTrip() {
        final String[] testStrings = { "", "a", "ab", "abc", "abcd", "abcde", "abcdef", "Hello World", "The quick brown fox jumps over the lazy dog",
                "1234567890", "!@#$%^&*()" };
        for (final String test : testStrings) {
            final byte[] input = test.getBytes(CHARSET_UTF8);
            final byte[] encoded = new Base58().encode(input);
            final byte[] decoded = new Base58().decode(encoded);
            assertArrayEquals(input, decoded, "Round trip failed for: " + test);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4 })
    void testRoundtripByte0(final int len) throws IOException {
        // Each zero byte encodes as '1' in Base58; verify that an all-zeros array
        // encodes to all '1' characters and decodes back to the original zeros.
        final byte[] zeros = new byte[len];
        final byte[] expectedEncoded = ArrayFill.fill(zeros.clone(), (byte) '1');
        assertArrayEquals(expectedEncoded, Base58.builder().get().encode(zeros));
        final byte[] decoded = Base58.builder().get().decode(expectedEncoded);
        assertArrayEquals(zeros, decoded, () -> String.format("zeros=%s, decoded=%s", Arrays.toString(zeros), Arrays.toString(decoded)));
    }

    @Test
    void testSingleBytes() {
        // Verify that every possible single-byte value (1-255) survives a round-trip
        for (int i = 1; i <= 255; i++) {
            final byte[] data = { (byte) i };
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEquals(data, dec, "Failed for byte value: " + i);
        }
    }

    @Test
    void testTestVectors() {
        // Known test vectors: string inputs
        final String content = "Hello World!";
        final String content1 = "The quick brown fox jumps over the lazy dog.";
        // 0x0000287fb4cd is a 6-byte value (two leading zero bytes).
        // Store it as a long so ByteBuffer can produce the 8-byte big-endian representation,
        // then trim the two most-significant padding bytes to get the 6-byte form.
        final long content2AsLong = 0x0000287fb4cdL;
        final byte[] content2Bytes = ByteBuffer.allocate(8).putLong(content2AsLong).array();
        final byte[] content2Trimmed = new byte[6];
        System.arraycopy(content2Bytes, 2, content2Trimmed, 0, 6);

        final byte[] encodedBytes = new Base58().encode(StringUtils.getBytesUtf8(content));
        final byte[] encodedBytes1 = new Base58().encode(StringUtils.getBytesUtf8(content1));
        final byte[] encodedBytes2 = new Base58().encode(content2Trimmed);

        assertEquals("2NEpo7TZRRrLZSi2U", StringUtils.newStringUtf8(encodedBytes), "encoding hello world");
        assertEquals("USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z", StringUtils.newStringUtf8(encodedBytes1));
        assertEquals("11233QC4", StringUtils.newStringUtf8(encodedBytes2), "encoding 0x0000287fb4cd");

        final byte[] decodedBytes = new Base58().decode(encodedBytes);
        final byte[] decodedBytes1 = new Base58().decode(encodedBytes1);
        final byte[] decodedBytes2 = new Base58().decode(encodedBytes2);

        assertEquals(content, StringUtils.newStringUtf8(decodedBytes), "decoding hello world");
        assertEquals(content1, StringUtils.newStringUtf8(decodedBytes1));
        assertArrayEquals(content2Trimmed, decodedBytes2, "decoding 0x0000287fb4cd");
    }
}
