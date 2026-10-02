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
package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

/**
 * Test for {@link ByteOrderMark}.
 */
class ByteOrderMarkTest {

    // Synthetic BOMs whose byte sequences are simple and predictable, so the
    // expected values in the assertions below are easy to follow. The number in
    // each name reflects how many bytes the BOM holds.
    private static final ByteOrderMark BOM_1_BYTE = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark BOM_2_BYTES = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark BOM_3_BYTES = new ByteOrderMark("test3", 1, 2, 3);

    /** Tests that {@link ByteOrderMark#getCharsetName()} can be loaded as a {@link java.nio.charset.Charset} as advertised. */
    @Test
    void testConstantCharsetNames() {
        // Charset.forName throws if the name is not a recognized charset, so a
        // non-null result confirms every predefined BOM advertises a real charset.
        assertNotNull(Charset.forName(ByteOrderMark.UTF_8.getCharsetName()));
        assertNotNull(Charset.forName(ByteOrderMark.UTF_16BE.getCharsetName()));
        assertNotNull(Charset.forName(ByteOrderMark.UTF_16LE.getCharsetName()));
        assertNotNull(Charset.forName(ByteOrderMark.UTF_32BE.getCharsetName()));
        assertNotNull(Charset.forName(ByteOrderMark.UTF_32LE.getCharsetName()));
    }

    /** Tests that the constructor rejects invalid charset names and byte arrays. */
    @Test
    void testConstructorExceptions() {
        // null charset name -> NPE; empty charset name -> IAE.
        assertThrows(NullPointerException.class, () -> new ByteOrderMark(null, 1, 2, 3));
        assertThrows(IllegalArgumentException.class, () -> new ByteOrderMark("", 1, 2, 3));
        // null bytes -> NPE; zero bytes -> IAE.
        assertThrows(NullPointerException.class, () -> new ByteOrderMark("a", (int[]) null));
        assertThrows(IllegalArgumentException.class, () -> new ByteOrderMark("b"));
    }

    /** Tests {@link ByteOrderMark#equals(Object)}. */
    @SuppressWarnings("EqualsWithItself")
    @Test
    void testEquals() {
        // Each predefined BOM equals itself.
        assertEquals(ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16BE);
        assertEquals(ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16LE);
        assertEquals(ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32BE);
        assertEquals(ByteOrderMark.UTF_32LE, ByteOrderMark.UTF_32LE);
        assertEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_8);

        // BOMs with different byte sequences are not equal.
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32BE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32LE);

        // The synthetic BOMs equal themselves.
        assertEquals(BOM_1_BYTE, BOM_1_BYTE, "test1 equals");
        assertEquals(BOM_2_BYTES, BOM_2_BYTES, "test2 equals");
        assertEquals(BOM_3_BYTES, BOM_3_BYTES, "test3 equals");

        // equals() depends only on the bytes, not the charset name: a non-BOM
        // object, a different byte value, or a different length all make it unequal.
        assertNotEquals(BOM_1_BYTE, new Object(), "Object not equal");
        assertNotEquals(BOM_1_BYTE, new ByteOrderMark("1a", 2), "test1-1 not equal");
        assertNotEquals(BOM_1_BYTE, new ByteOrderMark("1b", 1, 2), "test1-2 not test2");
        assertNotEquals(BOM_2_BYTES, new ByteOrderMark("2", 1, 1), "test2 not equal");
        assertNotEquals(BOM_3_BYTES, new ByteOrderMark("3", 1, 2, 4), "test3 not equal");
    }

    /** Tests {@link ByteOrderMark#getBytes()}, including that it returns a defensive copy. */
    @Test
    void testGetBytes() {
        assertArrayEquals(BOM_1_BYTE.getBytes(), new byte[] { (byte) 1 }, "test1 bytes");
        // Mutating the returned array must not affect the BOM, because getBytes()
        // hands back a fresh copy each call.
        BOM_1_BYTE.getBytes()[0] = 2;
        assertArrayEquals(BOM_1_BYTE.getBytes(), new byte[] { (byte) 1 }, "test1 bytes");
        assertArrayEquals(BOM_2_BYTES.getBytes(), new byte[] { (byte) 1, (byte) 2 }, "test1 bytes");
        assertArrayEquals(BOM_3_BYTES.getBytes(), new byte[] { (byte) 1, (byte) 2, (byte) 3 }, "test1 bytes");
    }

    /** Tests {@link ByteOrderMark#getCharsetName()}. */
    @Test
    void testGetCharsetName() {
        assertEquals("test1", BOM_1_BYTE.getCharsetName(), "test1 name");
        assertEquals("test2", BOM_2_BYTES.getCharsetName(), "test2 name");
        assertEquals("test3", BOM_3_BYTES.getCharsetName(), "test3 name");
    }

    /** Tests {@link ByteOrderMark#get(int)} returns the byte at each position. */
    @Test
    void testGetInt() {
        assertEquals(1, BOM_1_BYTE.get(0), "test1 get(0)");
        assertEquals(1, BOM_2_BYTES.get(0), "test2 get(0)");
        assertEquals(2, BOM_2_BYTES.get(1), "test2 get(1)");
        assertEquals(1, BOM_3_BYTES.get(0), "test3 get(0)");
        assertEquals(2, BOM_3_BYTES.get(1), "test3 get(1)");
        assertEquals(3, BOM_3_BYTES.get(2), "test3 get(2)");
    }

    /** Tests {@link ByteOrderMark#hashCode()}, which is the class hash plus the sum of the bytes. */
    @Test
    void testHashCode() {
        final int bomClassHash = ByteOrderMark.class.hashCode();
        // The expected hash is the class hash plus the sum of the BOM's bytes:
        // {1} -> +1, {1,2} -> +3, {1,2,3} -> +6.
        assertEquals(bomClassHash + 1, BOM_1_BYTE.hashCode(), "hash test1 ");
        assertEquals(bomClassHash + 3, BOM_2_BYTES.hashCode(), "hash test2 ");
        assertEquals(bomClassHash + 6, BOM_3_BYTES.hashCode(), "hash test3 ");
    }

    /** Tests {@link ByteOrderMark#length()} returns the number of bytes. */
    @Test
    void testLength() {
        assertEquals(1, BOM_1_BYTE.length(), "test1 length");
        assertEquals(2, BOM_2_BYTES.length(), "test2 length");
        assertEquals(3, BOM_3_BYTES.length(), "test3 length");
    }

    /** Tests {@link ByteOrderMark#matches(int[])}, which checks whether an array starts with the BOM's bytes. */
    @Test
    void testMatches() {
        // A BOM always matches its own bytes.
        assertTrue(ByteOrderMark.UTF_16BE.matches(ByteOrderMark.UTF_16BE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_16LE.matches(ByteOrderMark.UTF_16LE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_32BE.matches(ByteOrderMark.UTF_32BE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_16BE.matches(ByteOrderMark.UTF_16BE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_8.matches(ByteOrderMark.UTF_8.getRawBytes()));

        assertTrue(BOM_1_BYTE.matches(BOM_1_BYTE.getRawBytes()));
        assertTrue(BOM_2_BYTES.matches(BOM_2_BYTES.getRawBytes()));
        assertTrue(BOM_3_BYTES.matches(BOM_3_BYTES.getRawBytes()));

        // matches() is a prefix check: a differing first byte fails, but a longer
        // array that starts with the BOM's bytes ({1} is a prefix of {1,2}) succeeds.
        assertFalse(BOM_1_BYTE.matches(new ByteOrderMark("1a", 2).getRawBytes()));
        assertTrue(BOM_1_BYTE.matches(new ByteOrderMark("1b", 1, 2).getRawBytes()));
        assertFalse(BOM_2_BYTES.matches(new ByteOrderMark("2", 1, 1).getRawBytes()));
        assertFalse(BOM_3_BYTES.matches(new ByteOrderMark("3", 1, 2, 4).getRawBytes()));
    }

    /** Tests {@link ByteOrderMark#toString()}. */
    @Test
    void testToString() {
        assertEquals("ByteOrderMark[test1: 0x1]", BOM_1_BYTE.toString(), "test1 ");
        assertEquals("ByteOrderMark[test2: 0x1,0x2]", BOM_2_BYTES.toString(), "test2 ");
        assertEquals("ByteOrderMark[test3: 0x1,0x2,0x3]", BOM_3_BYTES.toString(), "test3 ");
    }
}
