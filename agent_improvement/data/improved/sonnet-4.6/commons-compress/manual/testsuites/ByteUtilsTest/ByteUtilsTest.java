/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.compress.utils.ByteUtils.InputStreamByteSupplier;
import org.apache.commons.compress.utils.ByteUtils.OutputStreamByteConsumer;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

class ByteUtilsTest {

    // Bytes {2, 3, 4} laid out in memory represent 0x040302 in little-endian order.
    private static final long VALUE_3BYTES = 0x040302L;

    // Bytes {2, 3, 4, 0x80} in little-endian: the high byte (128) has its MSB set,
    // so the result exceeds Integer.MAX_VALUE and must be held in a long.
    private static final long VALUE_4BYTES_UNSIGNED = 0x80040302L;

    // Byte sequences that act as both input and expected-output fixtures.
    private static final byte[] BYTES_2_3_4        = { 2, 3, 4 };
    private static final byte[] BYTES_2_3_4_128    = { 2, 3, 4, (byte) 128 };

    // -----------------------------------------------------------------------
    // fromLittleEndian – byte-array overloads
    // -----------------------------------------------------------------------

    @Test
    void testFromLittleEndianFromArray() {
        final byte[] b = { 1, 2, 3, 4, 5 };
        // Read 3 bytes starting at offset 1: bytes {2, 3, 4} → VALUE_3BYTES
        assertEquals(VALUE_3BYTES, fromLittleEndian(b, 1, 3));
    }

    @Test
    void testFromLittleEndianFromArrayOneArg() {
        assertEquals(VALUE_3BYTES, fromLittleEndian(BYTES_2_3_4));
    }

    @Test
    void testFromLittleEndianFromArrayOneArgUnsignedInt32() {
        // 128 in the most-significant byte position is treated as unsigned (0x80),
        // so the result is VALUE_4BYTES_UNSIGNED rather than a negative int.
        assertEquals(VALUE_4BYTES_UNSIGNED, fromLittleEndian(BYTES_2_3_4_128));
    }

    @Test
    void testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig() {
        // fromLittleEndian cannot read more than 8 bytes into a long.
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }));
    }

    @Test
    void testFromLittleEndianFromArrayThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(ArrayUtils.EMPTY_BYTE_ARRAY, 0, 9));
    }

    @Test
    void testFromLittleEndianFromArrayUnsignedInt32() {
        final byte[] b = { 1, 2, 3, 4, (byte) 128 };
        // Offset 1, length 4: bytes {2, 3, 4, 128} → VALUE_4BYTES_UNSIGNED
        assertEquals(VALUE_4BYTES_UNSIGNED, fromLittleEndian(b, 1, 4));
    }

    // -----------------------------------------------------------------------
    // fromLittleEndian – DataInput overload
    // -----------------------------------------------------------------------

    @Test
    void testFromLittleEndianFromDataInput() throws IOException {
        final DataInput din = new DataInputStream(
                new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 }));
        assertEquals(VALUE_3BYTES, fromLittleEndian(din, 3));
    }

    @Test
    void testFromLittleEndianFromDataInputUnsignedInt32() throws IOException {
        final DataInput din = new DataInputStream(
                new ByteArrayInputStream(BYTES_2_3_4_128));
        assertEquals(VALUE_4BYTES_UNSIGNED, fromLittleEndian(din, 4));
    }

    @Test
    void testFromLittleEndianFromDataInputThrowsForLengthTooBig() {
        final DataInput din = new DataInputStream(
                new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY));
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(din, 9));
    }

    @Test
    void testFromLittleEndianFromDataInputThrowsForPrematureEnd() {
        // Only 2 bytes available but 3 are requested → EOFException.
        final DataInput din = new DataInputStream(
                new ByteArrayInputStream(new byte[] { 2, 3 }));
        assertThrows(EOFException.class, () -> fromLittleEndian(din, 3));
    }

    // -----------------------------------------------------------------------
    // fromLittleEndian – InputStream overload (deprecated but still tested)
    // -----------------------------------------------------------------------

    @Test
    void testFromLittleEndianFromStream() throws IOException {
        final ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 });
        assertEquals(VALUE_3BYTES, fromLittleEndian(bin, 3));
    }

    @Test
    void testFromLittleEndianFromStreamUnsignedInt32() throws IOException {
        final ByteArrayInputStream bin = new ByteArrayInputStream(BYTES_2_3_4_128);
        assertEquals(VALUE_4BYTES_UNSIGNED, fromLittleEndian(bin, 4));
    }

    @Test
    void testFromLittleEndianFromStreamThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY), 9));
    }

    @Test
    void testFromLittleEndianFromStreamThrowsForPrematureEnd() {
        // Only 2 bytes available but 3 are requested → IOException.
        final ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] { 2, 3 });
        assertThrows(IOException.class, () -> fromLittleEndian(bin, 3));
    }

    // -----------------------------------------------------------------------
    // fromLittleEndian – ByteSupplier overload
    // -----------------------------------------------------------------------

    @Test
    void testFromLittleEndianFromSupplier() throws IOException {
        final ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 });
        assertEquals(VALUE_3BYTES, fromLittleEndian(new InputStreamByteSupplier(bin), 3));
    }

    @Test
    void testFromLittleEndianFromSupplierUnsignedInt32() throws IOException {
        final ByteArrayInputStream bin = new ByteArrayInputStream(BYTES_2_3_4_128);
        assertEquals(VALUE_4BYTES_UNSIGNED, fromLittleEndian(new InputStreamByteSupplier(bin), 4));
    }

    @Test
    void testFromLittleEndianFromSupplierThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(
                        new InputStreamByteSupplier(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY)), 9));
    }

    @Test
    void testFromLittleEndianFromSupplierThrowsForPrematureEnd() {
        // Only 2 bytes available but 3 are requested → IOException.
        final ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] { 2, 3 });
        assertThrows(IOException.class, () -> fromLittleEndian(new InputStreamByteSupplier(bin), 3));
    }

    // -----------------------------------------------------------------------
    // toLittleEndian – byte-array overload
    // -----------------------------------------------------------------------

    @Test
    void testToLittleEndianToByteArray() {
        final byte[] b = new byte[4];
        // Write VALUE_3BYTES into positions [1, 4) of the buffer.
        toLittleEndian(b, VALUE_3BYTES, 1, 3);
        assertArrayEquals(BYTES_2_3_4, Arrays.copyOfRange(b, 1, 4));
    }

    @Test
    void testToLittleEndianToByteArrayUnsignedInt32() {
        final byte[] b = new byte[4];
        toLittleEndian(b, VALUE_4BYTES_UNSIGNED, 0, 4);
        assertArrayEquals(BYTES_2_3_4_128, b);
    }

    // -----------------------------------------------------------------------
    // toLittleEndian – ByteConsumer overload
    // -----------------------------------------------------------------------

    @Test
    void testToLittleEndianToConsumer() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(bos), VALUE_3BYTES, 3);
            byteArray = bos.toByteArray();
            assertArrayEquals(BYTES_2_3_4, byteArray);
        }
        assertArrayEquals(BYTES_2_3_4, byteArray);
    }

    @Test
    void testToLittleEndianToConsumerUnsignedInt32() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(bos), VALUE_4BYTES_UNSIGNED, 4);
            byteArray = bos.toByteArray();
            assertArrayEquals(BYTES_2_3_4_128, byteArray);
        }
        assertArrayEquals(BYTES_2_3_4_128, byteArray);
    }

    // -----------------------------------------------------------------------
    // toLittleEndian – DataOutput overload (deprecated but still tested)
    // -----------------------------------------------------------------------

    @Test
    void testToLittleEndianToDataOutput() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            final DataOutput dos = new DataOutputStream(bos);
            toLittleEndian(dos, VALUE_3BYTES, 3);
            byteArray = bos.toByteArray();
            assertArrayEquals(BYTES_2_3_4, byteArray);
        }
        assertArrayEquals(BYTES_2_3_4, byteArray);
    }

    @Test
    void testToLittleEndianToDataOutputUnsignedInt32() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            final DataOutput dos = new DataOutputStream(bos);
            toLittleEndian(dos, VALUE_4BYTES_UNSIGNED, 4);
            byteArray = bos.toByteArray();
            assertArrayEquals(BYTES_2_3_4_128, byteArray);
        }
        assertArrayEquals(BYTES_2_3_4_128, byteArray);
    }

    // -----------------------------------------------------------------------
    // toLittleEndian – OutputStream overload
    // -----------------------------------------------------------------------

    @Test
    void testToLittleEndianToStream() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(bos, VALUE_3BYTES, 3);
            byteArray = bos.toByteArray();
            assertArrayEquals(BYTES_2_3_4, byteArray);
        }
        assertArrayEquals(BYTES_2_3_4, byteArray);
    }

    @Test
    void testToLittleEndianToStreamUnsignedInt32() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(bos, VALUE_4BYTES_UNSIGNED, 4);
            byteArray = bos.toByteArray();
            assertArrayEquals(BYTES_2_3_4_128, byteArray);
        }
        assertArrayEquals(BYTES_2_3_4_128, byteArray);
    }
}
