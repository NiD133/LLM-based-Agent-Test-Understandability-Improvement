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

    /** All 8 bits set — the byte value 0xFF. */
    private static final byte ALL_BITS = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    /** An instance of the binary codec. */
    BinaryCodec instance;

    /**
     * Creates a byte array whose elements are the truncated (byte) values of the supplied ints.
     * Allows inline construction of expected byte arrays without intermediate variable assignments.
     */
    private static byte[] b(final int... values) {
        final byte[] result = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = (byte) values[i];
        }
        return result;
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
        assertEquals(new String(bits), new String(decoded));
        if (encodeMe == null) {
            decoded = instance.decode((byte[]) null);
        } else {
            decoded = (byte[]) instance.decode((Object) encodeMe.getBytes(CHARSET_UTF8));
        }
        assertEquals(new String(bits), new String(decoded));
        if (encodeMe == null) {
            decoded = (byte[]) instance.decode((char[]) null);
        } else {
            decoded = (byte[]) instance.decode(encodeMe.toCharArray());
        }
        assertEquals(new String(bits), new String(decoded));
    }

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    /*
     * Tests for byte[] decode(byte[])
     */
    @Test
    void testDecodeByteArray() {
        // Single byte: progressively set bits 0 through 7
        assertEquals(new String(new byte[1]),                                         new String(instance.decode("00000000".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0)),                                            new String(instance.decode("00000001".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1)),                                   new String(instance.decode("00000011".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2)),                           new String(instance.decode("00000111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3)),                  new String(instance.decode("00001111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)),          new String(instance.decode("00011111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)), new String(instance.decode("00111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)), new String(instance.decode("01111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS)),                                         new String(instance.decode("11111111".getBytes(CHARSET_UTF8))));

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertEquals(new String(b(ALL_BITS, 0)),                                         new String(instance.decode("0000000011111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0)),                                    new String(instance.decode("0000000111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1)),                           new String(instance.decode("0000001111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2)),                  new String(instance.decode("0000011111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3)),          new String(instance.decode("0000111111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)), new String(instance.decode("0001111111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)),          new String(instance.decode("0011111111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)), new String(instance.decode("0111111111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, ALL_BITS)),                                 new String(instance.decode("1111111111111111".getBytes(CHARSET_UTF8))));
    }

    /**
     * Tests for Object decode(Object)
     */
    @Test
    void testDecodeObject() throws Exception {
        // Single byte: progressively set bits 0 through 7
        assertDecodeObject(new byte[1],                                                          "00000000");
        assertDecodeObject(b(BIT_0),                                                             "00000001");
        assertDecodeObject(b(BIT_0 | BIT_1),                                                    "00000011");
        assertDecodeObject(b(BIT_0 | BIT_1 | BIT_2),                                           "00000111");
        assertDecodeObject(b(BIT_0 | BIT_1 | BIT_2 | BIT_3),                                  "00001111");
        assertDecodeObject(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),                          "00011111");
        assertDecodeObject(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),                 "00111111");
        assertDecodeObject(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6),        "01111111");
        assertDecodeObject(b(ALL_BITS),                                                          "11111111");

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertDecodeObject(b(ALL_BITS, 0),                                                          "0000000011111111");
        assertDecodeObject(b(ALL_BITS, BIT_0),                                                     "0000000111111111");
        assertDecodeObject(b(ALL_BITS, BIT_0 | BIT_1),                                            "0000001111111111");
        assertDecodeObject(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2),                                   "0000011111111111");
        assertDecodeObject(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3),                          "0000111111111111");
        assertDecodeObject(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),                 "0001111111111111");
        assertDecodeObject(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),        "0011111111111111");
        assertDecodeObject(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6), "0111111111111111");
        assertDecodeObject(b(ALL_BITS, ALL_BITS),                                                   "1111111111111111");
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
        // Single byte: progressively set bits 0 through 7
        assertEquals("00000000", new String(instance.encode(new byte[1])));
        assertEquals("00000001", new String(instance.encode(b(BIT_0))));
        assertEquals("00000011", new String(instance.encode(b(BIT_0 | BIT_1))));
        assertEquals("00000111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2))));
        assertEquals("00001111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2 | BIT_3))));
        assertEquals("00011111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4))));
        assertEquals("00111111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5))));
        assertEquals("01111111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6))));
        assertEquals("11111111", new String(instance.encode(b(ALL_BITS))));

        // Two bytes: progressively set bits in low byte (index 0) while high byte (index 1) stays zero
        assertEquals("0000000000000000", new String(instance.encode(new byte[2])));
        assertEquals("0000000000000001", new String(instance.encode(b(BIT_0, 0))));
        assertEquals("0000000000000011", new String(instance.encode(b(BIT_0 | BIT_1, 0))));
        assertEquals("0000000000000111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2, 0))));
        assertEquals("0000000000001111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2 | BIT_3, 0))));
        assertEquals("0000000000011111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0))));
        assertEquals("0000000000111111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0))));
        assertEquals("0000000001111111", new String(instance.encode(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0))));
        assertEquals("0000000011111111", new String(instance.encode(b(ALL_BITS, 0))));

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertEquals("0000000111111111", new String(instance.encode(b(ALL_BITS, BIT_0))));
        assertEquals("0000001111111111", new String(instance.encode(b(ALL_BITS, BIT_0 | BIT_1))));
        assertEquals("0000011111111111", new String(instance.encode(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2))));
        assertEquals("0000111111111111", new String(instance.encode(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3))));
        assertEquals("0001111111111111", new String(instance.encode(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4))));
        assertEquals("0011111111111111", new String(instance.encode(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5))));
        assertEquals("0111111111111111", new String(instance.encode(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6))));
        assertEquals("1111111111111111", new String(instance.encode(b(ALL_BITS, ALL_BITS))));

        assertEquals(0, instance.encode((byte[]) null).length);
    }

    /*
     * Tests for Object encode(Object)
     */
    @Test
    void testEncodeObject() throws Exception {
        // Single byte: progressively set bits 0 through 7
        assertEquals("00000000", new String((char[]) instance.encode((Object) new byte[1])));
        assertEquals("00000001", new String((char[]) instance.encode((Object) b(BIT_0))));
        assertEquals("00000011", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1))));
        assertEquals("00000111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2))));
        assertEquals("00001111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2 | BIT_3))));
        assertEquals("00011111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4))));
        assertEquals("00111111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5))));
        assertEquals("01111111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6))));
        assertEquals("11111111", new String((char[]) instance.encode((Object) b(ALL_BITS))));

        // Two bytes: progressively set bits in low byte (index 0) while high byte (index 1) stays zero
        assertEquals("0000000000000000", new String((char[]) instance.encode((Object) new byte[2])));
        assertEquals("0000000000000001", new String((char[]) instance.encode((Object) b(BIT_0, 0))));
        assertEquals("0000000000000011", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1, 0))));
        assertEquals("0000000000000111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2, 0))));
        assertEquals("0000000000001111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2 | BIT_3, 0))));
        assertEquals("0000000000011111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0))));
        assertEquals("0000000000111111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0))));
        assertEquals("0000000001111111", new String((char[]) instance.encode((Object) b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0))));
        assertEquals("0000000011111111", new String((char[]) instance.encode((Object) b(ALL_BITS, 0))));

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertEquals("0000000111111111", new String((char[]) instance.encode((Object) b(ALL_BITS, BIT_0))));
        assertEquals("0000001111111111", new String((char[]) instance.encode((Object) b(ALL_BITS, BIT_0 | BIT_1))));
        assertEquals("0000011111111111", new String((char[]) instance.encode((Object) b(ALL_BITS, BIT_0 | BIT_1 | BIT_2))));
        assertEquals("0000111111111111", new String((char[]) instance.encode((Object) b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3))));
        assertEquals("0001111111111111", new String((char[]) instance.encode((Object) b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4))));
        assertEquals("0011111111111111", new String((char[]) instance.encode((Object) b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5))));
        assertEquals("0111111111111111", new String((char[]) instance.encode((Object) b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6))));
        assertEquals("1111111111111111", new String((char[]) instance.encode((Object) b(ALL_BITS, ALL_BITS))));
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
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".getBytes(CHARSET_UTF8)));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".getBytes(CHARSET_UTF8)));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".getBytes(CHARSET_UTF8)));

        // Single byte: progressively set bits 0 through 7
        assertEquals(new String(new byte[1]),                                         new String(BinaryCodec.fromAscii("00000000".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0)),                                            new String(BinaryCodec.fromAscii("00000001".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1)),                                   new String(BinaryCodec.fromAscii("00000011".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2)),                           new String(BinaryCodec.fromAscii("00000111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3)),                  new String(BinaryCodec.fromAscii("00001111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)),          new String(BinaryCodec.fromAscii("00011111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)), new String(BinaryCodec.fromAscii("00111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)), new String(BinaryCodec.fromAscii("01111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS)),                                         new String(BinaryCodec.fromAscii("11111111".getBytes(CHARSET_UTF8))));

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertEquals(new String(b(ALL_BITS, 0)),                                          new String(BinaryCodec.fromAscii("0000000011111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0)),                                     new String(BinaryCodec.fromAscii("0000000111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1)),                            new String(BinaryCodec.fromAscii("0000001111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2)),                   new String(BinaryCodec.fromAscii("0000011111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3)),           new String(BinaryCodec.fromAscii("0000111111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)),  new String(BinaryCodec.fromAscii("0001111111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)),          new String(BinaryCodec.fromAscii("0011111111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)), new String(BinaryCodec.fromAscii("0111111111111111".getBytes(CHARSET_UTF8))));
        assertEquals(new String(b(ALL_BITS, ALL_BITS)),                                  new String(BinaryCodec.fromAscii("1111111111111111".getBytes(CHARSET_UTF8))));

        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
    }

    /*
     * Tests for byte[] fromAscii(char[])
     */
    @Test
    void testFromAsciiCharArray() {
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new char[0]).length);
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".toCharArray()));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".toCharArray()));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".toCharArray()));

        // Single byte: progressively set bits 0 through 7
        assertEquals(new String(new byte[1]),                                         new String(BinaryCodec.fromAscii("00000000".toCharArray())));
        assertEquals(new String(b(BIT_0)),                                            new String(BinaryCodec.fromAscii("00000001".toCharArray())));
        assertEquals(new String(b(BIT_0 | BIT_1)),                                   new String(BinaryCodec.fromAscii("00000011".toCharArray())));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2)),                           new String(BinaryCodec.fromAscii("00000111".toCharArray())));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3)),                  new String(BinaryCodec.fromAscii("00001111".toCharArray())));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)),          new String(BinaryCodec.fromAscii("00011111".toCharArray())));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)), new String(BinaryCodec.fromAscii("00111111".toCharArray())));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)), new String(BinaryCodec.fromAscii("01111111".toCharArray())));
        assertEquals(new String(b(ALL_BITS)),                                         new String(BinaryCodec.fromAscii("11111111".toCharArray())));

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertEquals(new String(b(ALL_BITS, 0)),                                          new String(BinaryCodec.fromAscii("0000000011111111".toCharArray())));
        assertEquals(new String(b(ALL_BITS, BIT_0)),                                     new String(BinaryCodec.fromAscii("0000000111111111".toCharArray())));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1)),                            new String(BinaryCodec.fromAscii("0000001111111111".toCharArray())));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2)),                   new String(BinaryCodec.fromAscii("0000011111111111".toCharArray())));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3)),           new String(BinaryCodec.fromAscii("0000111111111111".toCharArray())));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)),  new String(BinaryCodec.fromAscii("0001111111111111".toCharArray())));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)),          new String(BinaryCodec.fromAscii("0011111111111111".toCharArray())));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)), new String(BinaryCodec.fromAscii("0111111111111111".toCharArray())));
        assertEquals(new String(b(ALL_BITS, ALL_BITS)),                                  new String(BinaryCodec.fromAscii("1111111111111111".toCharArray())));

        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
    }

    @Test
    void testToAsciiBytes() {
        // Single byte: progressively set bits 0 through 7
        assertEquals("00000000", new String(BinaryCodec.toAsciiBytes(new byte[1])));
        assertEquals("00000001", new String(BinaryCodec.toAsciiBytes(b(BIT_0))));
        assertEquals("00000011", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1))));
        assertEquals("00000111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2))));
        assertEquals("00001111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2 | BIT_3))));
        assertEquals("00011111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4))));
        assertEquals("00111111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5))));
        assertEquals("01111111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6))));
        assertEquals("11111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS))));

        // Two bytes: progressively set bits in low byte (index 0) while high byte (index 1) stays zero
        assertEquals("0000000000000000", new String(BinaryCodec.toAsciiBytes(new byte[2])));
        assertEquals("0000000000000001", new String(BinaryCodec.toAsciiBytes(b(BIT_0, 0))));
        assertEquals("0000000000000011", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1, 0))));
        assertEquals("0000000000000111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2, 0))));
        assertEquals("0000000000001111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2 | BIT_3, 0))));
        assertEquals("0000000000011111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0))));
        assertEquals("0000000000111111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0))));
        assertEquals("0000000001111111", new String(BinaryCodec.toAsciiBytes(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0))));
        assertEquals("0000000011111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS, 0))));

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertEquals("0000000111111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS, BIT_0))));
        assertEquals("0000001111111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS, BIT_0 | BIT_1))));
        assertEquals("0000011111111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2))));
        assertEquals("0000111111111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3))));
        assertEquals("0001111111111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4))));
        assertEquals("0011111111111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5))));
        assertEquals("0111111111111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6))));
        assertEquals("1111111111111111", new String(BinaryCodec.toAsciiBytes(b(ALL_BITS, ALL_BITS))));

        assertEquals(0, BinaryCodec.toAsciiBytes((byte[]) null).length);
    }

    @Test
    void testToAsciiChars() {
        // Single byte: progressively set bits 0 through 7
        assertEquals("00000000", new String(BinaryCodec.toAsciiChars(new byte[1])));
        assertEquals("00000001", new String(BinaryCodec.toAsciiChars(b(BIT_0))));
        assertEquals("00000011", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1))));
        assertEquals("00000111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2))));
        assertEquals("00001111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2 | BIT_3))));
        assertEquals("00011111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4))));
        assertEquals("00111111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5))));
        assertEquals("01111111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6))));
        assertEquals("11111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS))));

        // Two bytes: progressively set bits in low byte (index 0) while high byte (index 1) stays zero
        assertEquals("0000000000000000", new String(BinaryCodec.toAsciiChars(new byte[2])));
        assertEquals("0000000000000001", new String(BinaryCodec.toAsciiChars(b(BIT_0, 0))));
        assertEquals("0000000000000011", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1, 0))));
        assertEquals("0000000000000111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2, 0))));
        assertEquals("0000000000001111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2 | BIT_3, 0))));
        assertEquals("0000000000011111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0))));
        assertEquals("0000000000111111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0))));
        assertEquals("0000000001111111", new String(BinaryCodec.toAsciiChars(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0))));
        assertEquals("0000000011111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS, 0))));

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertEquals("0000000111111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS, BIT_0))));
        assertEquals("0000001111111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS, BIT_0 | BIT_1))));
        assertEquals("0000011111111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2))));
        assertEquals("0000111111111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3))));
        assertEquals("0001111111111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4))));
        assertEquals("0011111111111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5))));
        assertEquals("0111111111111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6))));
        assertEquals("1111111111111111", new String(BinaryCodec.toAsciiChars(b(ALL_BITS, ALL_BITS))));

        assertEquals(0, BinaryCodec.toAsciiChars((byte[]) null).length);
    }

    /**
     * Tests the toAsciiString(byte[]) method
     */
    @Test
    void testToAsciiString() {
        // Single byte: progressively set bits 0 through 7
        assertEquals("00000000", BinaryCodec.toAsciiString(new byte[1]));
        assertEquals("00000001", BinaryCodec.toAsciiString(b(BIT_0)));
        assertEquals("00000011", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1)));
        assertEquals("00000111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2)));
        assertEquals("00001111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2 | BIT_3)));
        assertEquals("00011111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)));
        assertEquals("00111111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)));
        assertEquals("01111111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)));
        assertEquals("11111111", BinaryCodec.toAsciiString(b(ALL_BITS)));

        // Two bytes: progressively set bits in low byte (index 0) while high byte (index 1) stays zero
        assertEquals("0000000000000000", BinaryCodec.toAsciiString(new byte[2]));
        assertEquals("0000000000000001", BinaryCodec.toAsciiString(b(BIT_0, 0)));
        assertEquals("0000000000000011", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1, 0)));
        assertEquals("0000000000000111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2, 0)));
        assertEquals("0000000000001111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2 | BIT_3, 0)));
        assertEquals("0000000000011111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0)));
        assertEquals("0000000000111111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0)));
        assertEquals("0000000001111111", BinaryCodec.toAsciiString(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0)));
        assertEquals("0000000011111111", BinaryCodec.toAsciiString(b(ALL_BITS, 0)));

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertEquals("0000000111111111", BinaryCodec.toAsciiString(b(ALL_BITS, BIT_0)));
        assertEquals("0000001111111111", BinaryCodec.toAsciiString(b(ALL_BITS, BIT_0 | BIT_1)));
        assertEquals("0000011111111111", BinaryCodec.toAsciiString(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2)));
        assertEquals("0000111111111111", BinaryCodec.toAsciiString(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3)));
        assertEquals("0001111111111111", BinaryCodec.toAsciiString(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)));
        assertEquals("0011111111111111", BinaryCodec.toAsciiString(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)));
        assertEquals("0111111111111111", BinaryCodec.toAsciiString(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)));
        assertEquals("1111111111111111", BinaryCodec.toAsciiString(b(ALL_BITS, ALL_BITS)));

        assertEquals("", BinaryCodec.toAsciiString(null));
    }

    /**
     * Tests for byte[] toByteArray(String)
     */
    @Test
    void testToByteArrayFromString() {
        // Single byte: progressively set bits 0 through 7
        assertEquals(new String(new byte[1]),                                         new String(instance.toByteArray("00000000")));
        assertEquals(new String(b(BIT_0)),                                            new String(instance.toByteArray("00000001")));
        assertEquals(new String(b(BIT_0 | BIT_1)),                                   new String(instance.toByteArray("00000011")));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2)),                           new String(instance.toByteArray("00000111")));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3)),                  new String(instance.toByteArray("00001111")));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)),          new String(instance.toByteArray("00011111")));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)), new String(instance.toByteArray("00111111")));
        assertEquals(new String(b(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)), new String(instance.toByteArray("01111111")));
        assertEquals(new String(b(ALL_BITS)),                                         new String(instance.toByteArray("11111111")));

        // Two bytes: low byte (index 0) always ALL_BITS; progressively set bits in high byte (index 1)
        assertEquals(new String(b(ALL_BITS, 0)),                                          new String(instance.toByteArray("0000000011111111")));
        assertEquals(new String(b(ALL_BITS, BIT_0)),                                     new String(instance.toByteArray("0000000111111111")));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1)),                            new String(instance.toByteArray("0000001111111111")));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2)),                   new String(instance.toByteArray("0000011111111111")));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3)),           new String(instance.toByteArray("0000111111111111")));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)),  new String(instance.toByteArray("0001111111111111")));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)),          new String(instance.toByteArray("0011111111111111")));
        assertEquals(new String(b(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)), new String(instance.toByteArray("0111111111111111")));
        assertEquals(new String(b(ALL_BITS, ALL_BITS)),                                  new String(instance.toByteArray("1111111111111111")));

        assertEquals(0, instance.toByteArray((String) null).length);
    }

}
