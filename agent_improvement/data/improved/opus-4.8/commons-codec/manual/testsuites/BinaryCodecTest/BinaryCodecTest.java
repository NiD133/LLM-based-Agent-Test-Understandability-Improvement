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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TestCase for BinaryCodec class.
 *
 * <p>
 * BinaryCodec converts between raw bytes and their textual "0"/"1" rendering. Every conversion in
 * both directions is exercised against the same fixed set of {@link Conversion} samples, so the bulk
 * of each test is expressed as: pick a conversion method, then assert it matches every sample. The
 * sample tables and the small {@link #assertEncodes}/{@link #assertDecodes} drivers below replace
 * what was previously hundreds of lines of hand-unrolled, near-identical assertions.
 * </p>
 */
class BinaryCodecTest {

    /**
     * A raw binary value paired with its expected ASCII "0"/"1" rendering.
     *
     * <p>
     * In {@link #rawBytes}, index 0 is the least-significant byte: BinaryCodec renders it as the
     * right-most eight characters of {@link #ascii}. For example {@code (0xFF, 0x01)} renders as
     * {@code "0000000111111111"} ("00000001" for byte 1 followed by "11111111" for byte 0).
     * </p>
     */
    private static final class Conversion {

        final byte[] rawBytes;
        final String ascii;

        Conversion(final String ascii, final int... rawBytes) {
            this.ascii = ascii;
            this.rawBytes = toBytes(rawBytes);
        }
    }

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /** Single-byte samples: each value from {@code 0x00} up to {@code 0xFF} as a left-filled run of 1s. */
    private static final Conversion[] SINGLE_BYTE = {
        new Conversion("00000000", 0x00),
        new Conversion("00000001", 0x01),
        new Conversion("00000011", 0x03),
        new Conversion("00000111", 0x07),
        new Conversion("00001111", 0x0F),
        new Conversion("00011111", 0x1F),
        new Conversion("00111111", 0x3F),
        new Conversion("01111111", 0x7F),
        new Conversion("11111111", 0xFF),
    };

    /**
     * Two-byte samples used by the decoding tests: a full low byte ({@code 0xFF}) plus a growing run
     * of 1s in the high byte.
     */
    private static final Conversion[] TWO_BYTE_DECODE = {
        new Conversion("0000000011111111", 0xFF, 0x00),
        new Conversion("0000000111111111", 0xFF, 0x01),
        new Conversion("0000001111111111", 0xFF, 0x03),
        new Conversion("0000011111111111", 0xFF, 0x07),
        new Conversion("0000111111111111", 0xFF, 0x0F),
        new Conversion("0001111111111111", 0xFF, 0x1F),
        new Conversion("0011111111111111", 0xFF, 0x3F),
        new Conversion("0111111111111111", 0xFF, 0x7F),
        new Conversion("1111111111111111", 0xFF, 0xFF),
    };

    /**
     * Two-byte samples used by the encoding tests: first a growing run of 1s in the low byte (high
     * byte empty), then a full low byte with a growing run of 1s in the high byte.
     */
    private static final Conversion[] TWO_BYTE_ENCODE = {
        new Conversion("0000000000000000", 0x00, 0x00),
        new Conversion("0000000000000001", 0x01, 0x00),
        new Conversion("0000000000000011", 0x03, 0x00),
        new Conversion("0000000000000111", 0x07, 0x00),
        new Conversion("0000000000001111", 0x0F, 0x00),
        new Conversion("0000000000011111", 0x1F, 0x00),
        new Conversion("0000000000111111", 0x3F, 0x00),
        new Conversion("0000000001111111", 0x7F, 0x00),
        new Conversion("0000000011111111", 0xFF, 0x00),
        new Conversion("0000000111111111", 0xFF, 0x01),
        new Conversion("0000001111111111", 0xFF, 0x03),
        new Conversion("0000011111111111", 0xFF, 0x07),
        new Conversion("0000111111111111", 0xFF, 0x0F),
        new Conversion("0001111111111111", 0xFF, 0x1F),
        new Conversion("0011111111111111", 0xFF, 0x3F),
        new Conversion("0111111111111111", 0xFF, 0x7F),
        new Conversion("1111111111111111", 0xFF, 0xFF),
    };

    /** Builds a byte array from int literals, so samples can be written in readable hex. */
    private static byte[] toBytes(final int... values) {
        final byte[] result = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = (byte) values[i];
        }
        return result;
    }

    /** An instance of the binary codec. */
    private BinaryCodec instance;

    @BeforeEach
    void setUp() {
        this.instance = new BinaryCodec();
    }

    /**
     * Asserts that {@code encoder} turns each sample's raw bytes into the sample's expected ASCII text,
     * across both the single-byte and two-byte encoding samples.
     *
     * @param encoder a raw-bytes-to-ASCII-text view of the conversion method under test.
     */
    private static void assertEncodes(final Function<byte[], String> encoder) {
        for (final Conversion sample : SINGLE_BYTE) {
            assertEquals(sample.ascii, encoder.apply(sample.rawBytes));
        }
        for (final Conversion sample : TWO_BYTE_ENCODE) {
            assertEquals(sample.ascii, encoder.apply(sample.rawBytes));
        }
    }

    /**
     * Asserts that {@code decoder} turns each sample's ASCII text back into the sample's raw bytes,
     * across both the single-byte and two-byte decoding samples. Decoded bytes are compared via their
     * String form, matching the original tests.
     *
     * @param decoder an ASCII-text-to-raw-bytes view of the conversion method under test.
     */
    private static void assertDecodes(final Function<String, byte[]> decoder) {
        for (final Conversion sample : SINGLE_BYTE) {
            assertEquals(new String(sample.rawBytes), new String(decoder.apply(sample.ascii)));
        }
        for (final Conversion sample : TWO_BYTE_DECODE) {
            assertEquals(new String(sample.rawBytes), new String(decoder.apply(sample.ascii)));
        }
    }

    /**
     * Asserts that the three {@code Object decode(...)} overloads (String, byte[], char[]) all recover
     * the expected raw bytes for the given ASCII text. A {@code null} {@code ascii} exercises the
     * {@code null} handling of the byte[] and char[] overloads.
     *
     * @param expectedRaw the raw bytes the ASCII text should decode to.
     * @param ascii the ASCII "0"/"1" text to decode, or {@code null}.
     */
    private void assertDecodeObject(final byte[] expectedRaw, final String ascii) throws DecoderException {
        final String expected = new String(expectedRaw);

        assertEquals(expected, new String((byte[]) instance.decode(ascii)));

        final byte[] fromBytes = ascii == null
            ? instance.decode((byte[]) null)
            : (byte[]) instance.decode((Object) ascii.getBytes(CHARSET_UTF8));
        assertEquals(expected, new String(fromBytes));

        final byte[] fromChars = ascii == null
            ? (byte[]) instance.decode((char[]) null)
            : (byte[]) instance.decode(ascii.toCharArray());
        assertEquals(expected, new String(fromChars));
    }

    /*
     * Tests for byte[] decode(byte[])
     */
    @Test
    void testDecodeByteArray() {
        assertDecodes(ascii -> instance.decode(ascii.getBytes(CHARSET_UTF8)));
    }

    /**
     * Tests for Object decode(Object)
     */
    @Test
    void testDecodeObject() throws Exception {
        for (final Conversion sample : SINGLE_BYTE) {
            assertDecodeObject(sample.rawBytes, sample.ascii);
        }
        for (final Conversion sample : TWO_BYTE_DECODE) {
            assertDecodeObject(sample.rawBytes, sample.ascii);
        }
        assertDecodeObject(new byte[0], null);
    }

    /**
     * Tests for Object decode(Object)
     */
    @Test
    void testDecodeObjectException() {
        assertThrows(DecoderException.class, () -> instance.decode(new Object()));
    }

    /*
     * Tests for byte[] encode(byte[])
     */
    @Test
    void testEncodeByteArray() {
        assertEncodes(raw -> new String(instance.encode(raw)));
        assertEquals(0, instance.encode((byte[]) null).length);
    }

    /*
     * Tests for Object encode(Object)
     */
    @Test
    void testEncodeObject() {
        assertEncodes(raw -> {
            try {
                return new String((char[]) instance.encode((Object) raw));
            } catch (final EncoderException e) {
                throw new AssertionError(e);
            }
        });
    }

    /*
     * Tests for Object encode(Object)
     */
    @Test
    void testEncodeObjectException() {
        assertThrows(EncoderException.class, () -> instance.encode(""));
    }

    /*
     * Tests for Object encode(Object)
     */
    @Test
    void testEncodeObjectNull() throws Exception {
        final Object obj = new byte[0];
        assertEquals(0, ((char[]) instance.encode(obj)).length);
    }

    /*
     * Tests for byte[] fromAscii(byte[])
     */
    @Test
    void testFromAsciiByteArray() {
        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new byte[0]).length);
        // Inputs shorter than 8 chars decode to nothing; only whole bytes (8 chars) are decoded.
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".getBytes(CHARSET_UTF8)));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".getBytes(CHARSET_UTF8)));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".getBytes(CHARSET_UTF8)));

        assertDecodes(ascii -> BinaryCodec.fromAscii(ascii.getBytes(CHARSET_UTF8)));

        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
    }

    /*
     * Tests for byte[] fromAscii(char[])
     */
    @Test
    void testFromAsciiCharArray() {
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new char[0]).length);
        // Inputs shorter than 8 chars decode to nothing; only whole bytes (8 chars) are decoded.
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".toCharArray()));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".toCharArray()));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".toCharArray()));

        assertDecodes(ascii -> BinaryCodec.fromAscii(ascii.toCharArray()));

        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
    }

    @Test
    void testToAsciiBytes() {
        assertEncodes(raw -> new String(BinaryCodec.toAsciiBytes(raw)));
        assertEquals(0, BinaryCodec.toAsciiBytes((byte[]) null).length);
    }

    @Test
    void testToAsciiChars() {
        assertEncodes(raw -> new String(BinaryCodec.toAsciiChars(raw)));
        assertEquals(0, BinaryCodec.toAsciiChars((byte[]) null).length);
    }

    /**
     * Tests the toAsciiString(byte[]) method
     */
    @Test
    void testToAsciiString() {
        assertEncodes(BinaryCodec::toAsciiString);
        assertEquals("", BinaryCodec.toAsciiString(null));
    }

    /**
     * Tests for byte[] toByteArray(String)
     */
    @Test
    void testToByteArrayFromString() {
        assertDecodes(ascii -> instance.toByteArray(ascii));
        assertEquals(0, instance.toByteArray((String) null).length);
    }
}
