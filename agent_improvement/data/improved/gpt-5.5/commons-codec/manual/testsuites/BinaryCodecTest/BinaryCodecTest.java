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

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TestCase for BinaryCodec class.
 */
class BinaryCodecTest {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /** Mask with bit zero-based index 0 raised. */
    private static final int BIT_0 = 0x01;

    /** Mask with bit zero-based index 1 raised. */
    private static final int BIT_1 = 0x02;

    /** Mask with bit zero-based index 2 raised. */
    private static final int BIT_2 = 0x04;

    /** Mask with bit zero-based index 3 raised. */
    private static final int BIT_3 = 0x08;

    /** Mask with bit zero-based index 4 raised. */
    private static final int BIT_4 = 0x10;

    /** Mask with bit zero-based index 5 raised. */
    private static final int BIT_5 = 0x20;

    /** Mask with bit zero-based index 6 raised. */
    private static final int BIT_6 = 0x40;

    /** Mask with bit zero-based index 7 raised. */
    private static final int BIT_7 = 0x80;

    private static final int FULL_BYTE = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7;

    private static final CodecCase[] DECODE_CASES = {
        codecCase("00000000", 0),
        codecCase("00000001", BIT_0),
        codecCase("00000011", BIT_0 | BIT_1),
        codecCase("00000111", BIT_0 | BIT_1 | BIT_2),
        codecCase("00001111", BIT_0 | BIT_1 | BIT_2 | BIT_3),
        codecCase("00011111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),
        codecCase("00111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),
        codecCase("01111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6),
        codecCase("11111111", FULL_BYTE),
        codecCase("0000000011111111", FULL_BYTE, 0),
        codecCase("0000000111111111", FULL_BYTE, BIT_0),
        codecCase("0000001111111111", FULL_BYTE, BIT_0 | BIT_1),
        codecCase("0000011111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2),
        codecCase("0000111111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2 | BIT_3),
        codecCase("0001111111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),
        codecCase("0011111111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),
        codecCase("0111111111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6),
        codecCase("1111111111111111", FULL_BYTE, FULL_BYTE)
    };

    private static final CodecCase[] ENCODE_CASES = {
        codecCase("00000000", 0),
        codecCase("00000001", BIT_0),
        codecCase("00000011", BIT_0 | BIT_1),
        codecCase("00000111", BIT_0 | BIT_1 | BIT_2),
        codecCase("00001111", BIT_0 | BIT_1 | BIT_2 | BIT_3),
        codecCase("00011111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),
        codecCase("00111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),
        codecCase("01111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6),
        codecCase("11111111", FULL_BYTE),
        codecCase("0000000000000000", 0, 0),
        codecCase("0000000000000001", BIT_0, 0),
        codecCase("0000000000000011", BIT_0 | BIT_1, 0),
        codecCase("0000000000000111", BIT_0 | BIT_1 | BIT_2, 0),
        codecCase("0000000000001111", BIT_0 | BIT_1 | BIT_2 | BIT_3, 0),
        codecCase("0000000000011111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0),
        codecCase("0000000000111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0),
        codecCase("0000000001111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0),
        codecCase("0000000011111111", FULL_BYTE, 0),
        codecCase("0000000111111111", FULL_BYTE, BIT_0),
        codecCase("0000001111111111", FULL_BYTE, BIT_0 | BIT_1),
        codecCase("0000011111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2),
        codecCase("0000111111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2 | BIT_3),
        codecCase("0001111111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),
        codecCase("0011111111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),
        codecCase("0111111111111111", FULL_BYTE, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6),
        codecCase("1111111111111111", FULL_BYTE, FULL_BYTE)
    };

    /** An instance of the binary codec. */
    BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    @Test
    void testDecodeByteArray() {
        for (final CodecCase testCase : DECODE_CASES) {
            assertDecodedBytes(testCase.raw, instance.decode(testCase.ascii.getBytes(CHARSET_UTF8)));
        }
    }

    @Test
    void testDecodeObject() throws Exception {
        for (final CodecCase testCase : DECODE_CASES) {
            assertDecodeObject(testCase.raw, testCase.ascii);
        }
        assertDecodeObject(new byte[0], null);
    }

    @Test
    void testDecodeObjectException() {
        assertThrows(DecoderException.class, () -> this.instance.decode(new Object()));
    }

    @Test
    void testEncodeByteArray() {
        for (final CodecCase testCase : ENCODE_CASES) {
            assertEquals(testCase.ascii, new String(instance.encode(testCase.raw)));
        }
        assertEquals(0, instance.encode((byte[]) null).length);
    }

    @Test
    void testEncodeObject() throws Exception {
        for (final CodecCase testCase : ENCODE_CASES) {
            assertEquals(testCase.ascii, new String((char[]) instance.encode((Object) testCase.raw)));
        }
    }

    @Test
    void testEncodeObjectException() {
        assertThrows(EncoderException.class, () -> instance.encode(""));
    }

    @Test
    void testEncodeObjectNull() throws Exception {
        final Object obj = new byte[0];
        assertEquals(0, ((char[]) instance.encode(obj)).length);
    }

    @Test
    void testFromAsciiByteArray() {
        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new byte[0]).length);
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".getBytes(CHARSET_UTF8)));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".getBytes(CHARSET_UTF8)));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".getBytes(CHARSET_UTF8)));
        for (final CodecCase testCase : DECODE_CASES) {
            assertDecodedBytes(testCase.raw, BinaryCodec.fromAscii(testCase.ascii.getBytes(CHARSET_UTF8)));
        }
        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
    }

    @Test
    void testFromAsciiCharArray() {
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new char[0]).length);
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".toCharArray()));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".toCharArray()));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".toCharArray()));
        for (final CodecCase testCase : DECODE_CASES) {
            assertDecodedBytes(testCase.raw, BinaryCodec.fromAscii(testCase.ascii.toCharArray()));
        }
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
    }

    @Test
    void testToAsciiBytes() {
        for (final CodecCase testCase : ENCODE_CASES) {
            assertEquals(testCase.ascii, new String(BinaryCodec.toAsciiBytes(testCase.raw)));
        }
        assertEquals(0, BinaryCodec.toAsciiBytes((byte[]) null).length);
    }

    @Test
    void testToAsciiChars() {
        for (final CodecCase testCase : ENCODE_CASES) {
            assertEquals(testCase.ascii, new String(BinaryCodec.toAsciiChars(testCase.raw)));
        }
        assertEquals(0, BinaryCodec.toAsciiChars((byte[]) null).length);
    }

    @Test
    void testToAsciiString() {
        for (final CodecCase testCase : ENCODE_CASES) {
            assertEquals(testCase.ascii, BinaryCodec.toAsciiString(testCase.raw));
        }
        assertEquals("", BinaryCodec.toAsciiString(null));
    }

    @Test
    void testToByteArrayFromString() {
        for (final CodecCase testCase : DECODE_CASES) {
            assertDecodedBytes(testCase.raw, instance.toByteArray(testCase.ascii));
        }
        assertEquals(0, instance.toByteArray((String) null).length);
    }

    /**
     * Utility used to assert the encoded and decoded values.
     *
     * @param bits
     *            the pre-encoded data
     * @param encodeMe
     *            data to encode and compare
     */
    void assertDecodeObject(final byte[] bits, final String encodeMe) throws DecoderException {
        byte[] decoded;
        decoded = (byte[]) instance.decode(encodeMe);
        assertDecodedBytes(bits, decoded);
        if (encodeMe == null) {
            decoded = instance.decode((byte[]) null);
        } else {
            decoded = (byte[]) instance.decode((Object) encodeMe.getBytes(CHARSET_UTF8));
        }
        assertDecodedBytes(bits, decoded);
        if (encodeMe == null) {
            decoded = (byte[]) instance.decode((char[]) null);
        } else {
            decoded = (byte[]) instance.decode(encodeMe.toCharArray());
        }
        assertDecodedBytes(bits, decoded);
    }

    private static void assertDecodedBytes(final byte[] expected, final byte[] actual) {
        assertEquals(new String(expected), new String(actual));
    }

    private static CodecCase codecCase(final String ascii, final int... raw) {
        return new CodecCase(ascii, raw(raw));
    }

    private static byte[] raw(final int... values) {
        final byte[] raw = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            raw[i] = (byte) values[i];
        }
        return raw;
    }

    private static final class CodecCase {

        private final String ascii;
        private final byte[] raw;

        private CodecCase(final String ascii, final byte[] raw) {
            this.ascii = ascii;
            this.raw = raw;
        }
    }
}
