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
import java.util.concurrent.atomic.AtomicInteger;

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

    /** Exclusive upper bound for the random payload length used in round-trip tests. */
    private static final int MAX_RANDOM_LENGTH = 10_000;

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * Asserts that decoding restored the original bytes, labelling any failure with the loop
     * index {@code i} (used as the data length / fill value across the round-trip tests).
     */
    private static void assertArrayEqualsAt(final byte[] data, final byte[] dec, final int i) {
        final AtomicInteger counter = new AtomicInteger(i);
        assertArrayEquals(data, dec, () -> String.format("Failed for length %,d: %s", counter.get(), Arrays.toString(data)));
    }

    private final Random random = new Random();

    @Test
    void testBase58() {
        final String content = "Hello World";
        final byte[] encodedBytes = new Base58().encode(StringUtils.getBytesUtf8(content));
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);
        assertEquals("JxF12TrwUP45BMd", encodedContent, "encoding hello world");
        final byte[] decodedBytes = new Base58().decode(encodedBytes);
        final String decodedContent = StringUtils.newStringUtf8(decodedBytes);
        assertEquals(content, decodedContent, "decoding hello world");
    }

    @Test
    void testEmptyBase58() {
        // Encoding empty input yields empty output; encoding null yields null.
        final byte[] emptyInput = {};
        assertEquals(0, new Base58().encode(emptyInput).length, "empty Base58 encode");
        assertNull(new Base58().encode(null), "empty Base58 encode");
        // Decoding empty input yields empty output; decoding null yields null.
        final byte[] emptyEncoded = new byte[0];
        assertEquals(0, new Base58().decode(emptyEncoded).length, "empty Base58 decode");
        assertNull(new Base58().decode((byte[]) null), "empty Base58 decode");
    }

    @Test
    void testEncodeDecode() {
        // A few large payloads filled with a constant byte must survive a round trip.
        for (int fillValue = 1; fillValue < 5; fillValue++) {
            final byte[] data = new byte[random.nextInt(MAX_RANDOM_LENGTH) + 1];
            Arrays.fill(data, (byte) fillValue);
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEqualsAt(data, dec, fillValue);
        }
    }

    @Test
    void testEncodeDecodeRandom() {
        // A few large random payloads must survive a round trip.
        for (int i = 1; i < 5; i++) {
            final byte[] data = new byte[random.nextInt(MAX_RANDOM_LENGTH) + 1];
            random.nextBytes(data);
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEqualsAt(data, dec, i);
        }
    }

    @Test
    void testEncodeDecodeSmall() {
        // Small payloads (lengths 0..11) filled with a constant byte must survive a round trip.
        for (int length = 0; length < 12; length++) {
            final byte[] data = new byte[length];
            Arrays.fill(data, (byte) length);
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEqualsAt(data, dec, length);
        }
    }

    @Test
    void testEncodeDecodeSmallRandom() {
        // Small random payloads (lengths 0..11) must survive a round trip.
        for (int length = 0; length < 12; length++) {
            final byte[] data = new byte[length];
            random.nextBytes(data);
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEqualsAt(data, dec, length);
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
        // '0', 'O', 'I' and 'l' are deliberately excluded from the Base58 alphabet,
        // so decoding them must fail.
        final byte[] invalidChars = "0OIl".getBytes(CHARSET_UTF8);
        assertThrows(IllegalArgumentException.class, () -> new Base58().decode(invalidChars));
    }

    @Test
    void testIsInAlphabet() {
        final Base58 base58 = new Base58();
        // Valid characters: digits 1-9 and letters, skipping the excluded I, O and l.
        for (char c = '1'; c <= '9'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'A'; c <= 'H'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'J'; c <= 'N'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'P'; c <= 'Z'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'a'; c <= 'k'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'm'; c <= 'z'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Characters explicitly excluded from the Base58 alphabet.
        assertFalse(base58.isInAlphabet((byte) '0'), "char 0");
        assertFalse(base58.isInAlphabet((byte) 'O'), "char O");
        assertFalse(base58.isInAlphabet((byte) 'I'), "char I");
        assertFalse(base58.isInAlphabet((byte) 'l'), "char l");
        // Byte values outside the printable alphabet range.
        assertFalse(base58.isInAlphabet((byte) -1));
        assertFalse(base58.isInAlphabet((byte) 0));
        assertFalse(base58.isInAlphabet((byte) 128));
        assertFalse(base58.isInAlphabet((byte) 255));
    }

    @Test
    void testLeadingZeros() {
        // Each leading zero byte is encoded as a single '1' character.
        final byte[] input = { 0, 0, 1, 2, 3 };
        final byte[] encoded = new Base58().encode(input);
        final String encodedStr = new String(encoded);
        // Two leading zero bytes -> the encoding starts with "11".
        assertTrue(encodedStr.startsWith("11"), "Leading zeros should encode as '1' characters");
        // Decoding restores the leading zeros.
        final byte[] decoded = new Base58().decode(encoded);
        assertArrayEquals(input, decoded, "Decoded should match original including leading zeros");
    }

    @Test
    void testObjectDecodeWithInvalidParameter() {
        // The Object overload of decode rejects anything that is not a byte[].
        assertThrows(DecoderException.class, () -> new Base58().decode(Integer.valueOf(5)));
    }

    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object encoded = new Base58().encode(original.getBytes(CHARSET_UTF8));
        final Base58 base58 = new Base58();
        final Object decoded = base58.decode(encoded);
        final String roundTripped = new String((byte[]) decoded);
        assertEquals(original, roundTripped, "dest string does not equal original");
    }

    @Test
    void testObjectEncodeWithInvalidParameter() {
        // The Object overload of encode rejects anything that is not a byte[].
        assertThrows(EncoderException.class, () -> new Base58().encode("Yadayadayada"));
    }

    @Test
    void testObjectEncodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object originalBytes = original.getBytes(CHARSET_UTF8);
        final Object encoded = new Base58().encode(originalBytes);
        final byte[] decoded = new Base58().decode((byte[]) encoded);
        final String roundTripped = new String(decoded);
        assertEquals(original, roundTripped, "dest string does not equal original");
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
        // An all-zero payload of length 'len' must encode to 'len' '1' characters, and decode back.
        final byte[] zeros = new byte[len];
        final byte[] encoded0s = ArrayFill.fill(zeros.clone(), (byte) '1');
        assertArrayEquals(encoded0s, Base58.builder().get().encode(zeros));
        final byte[] decoded = Base58.builder().get().decode(encoded0s);
        assertArrayEquals(zeros, decoded, () -> String.format("zeros=%s, decoded=%s", Arrays.toString(zeros), Arrays.toString(decoded)));
    }

    @Test
    void testSingleBytes() {
        // Every single-byte value must survive a round trip.
        for (int byteValue = 1; byteValue <= 255; byteValue++) {
            final byte[] data = { (byte) byteValue };
            final byte[] enc = new Base58().encode(data);
            final byte[] dec = new Base58().decode(enc);
            assertArrayEquals(data, dec, "Failed for byte value: " + byteValue);
        }
    }

    @Test
    void testTestVectors() {
        // Known-answer vectors for Base58 encoding and decoding.
        final String helloWorld = "Hello World!";
        final String pangram = "The quick brown fox jumps over the lazy dog.";
        final long numericValue = 0x0000287fb4cdL; // Use long to preserve the full 48-bit value.

        // Take the low 6 bytes (big-endian) of the 48-bit numeric value.
        final byte[] numericValueBytes = ByteBuffer.allocate(8).putLong(numericValue).array();
        final byte[] numericValueTrimmed = new byte[6];
        System.arraycopy(numericValueBytes, 2, numericValueTrimmed, 0, 6);

        final byte[] encodedHelloWorld = new Base58().encode(StringUtils.getBytesUtf8(helloWorld));
        final byte[] encodedPangram = new Base58().encode(StringUtils.getBytesUtf8(pangram));
        final byte[] encodedNumericValue = new Base58().encode(numericValueTrimmed);

        assertEquals("2NEpo7TZRRrLZSi2U", StringUtils.newStringUtf8(encodedHelloWorld), "encoding hello world");
        assertEquals("USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z", StringUtils.newStringUtf8(encodedPangram));
        assertEquals("11233QC4", StringUtils.newStringUtf8(encodedNumericValue), "encoding 0x0000287fb4cd");

        final byte[] decodedHelloWorld = new Base58().decode(encodedHelloWorld);
        final byte[] decodedPangram = new Base58().decode(encodedPangram);
        final byte[] decodedNumericValue = new Base58().decode(encodedNumericValue);

        assertEquals(helloWorld, StringUtils.newStringUtf8(decodedHelloWorld), "decoding hello world");
        assertEquals(pangram, StringUtils.newStringUtf8(decodedPangram));
        assertArrayEquals(numericValueTrimmed, decodedNumericValue, "decoding 0x0000287fb4cd");
    }
}
