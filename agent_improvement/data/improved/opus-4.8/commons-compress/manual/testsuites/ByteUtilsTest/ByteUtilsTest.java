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

    /** Little-endian byte sequence whose numeric value is {@link #VALUE_OF_3_BYTES}. */
    private static final byte[] LITTLE_ENDIAN_3_BYTES = { 2, 3, 4 };

    /** Little-endian byte sequence whose numeric value is {@link #VALUE_OF_4_BYTES}; the top byte makes it an unsigned int32. */
    private static final byte[] LITTLE_ENDIAN_4_BYTES = { 2, 3, 4, (byte) 128 };

    /** The number encoded by {@link #LITTLE_ENDIAN_3_BYTES} (bytes 2, 3, 4 in increasing significance). */
    private static final long VALUE_OF_3_BYTES = 2 + 3 * 256 + 4 * 256 * 256;

    /** The number encoded by {@link #LITTLE_ENDIAN_4_BYTES}; the high byte (128) exceeds the signed int range. */
    private static final long VALUE_OF_4_BYTES = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

    /** A length the conversion methods reject because a long holds at most eight bytes. */
    private static final int LENGTH_EXCEEDING_LONG = 9;

    @Test
    void testFromLittleEndianFromArray() {
        // Read only the {2, 3, 4} slice (offset 1, length 3), ignoring the surrounding 1 and 5.
        final byte[] bytes = { 1, 2, 3, 4, 5 };
        assertEquals(VALUE_OF_3_BYTES, fromLittleEndian(bytes, 1, 3));
    }

    @Test
    void testFromLittleEndianFromArrayOneArg() {
        assertEquals(VALUE_OF_3_BYTES, fromLittleEndian(LITTLE_ENDIAN_3_BYTES));
    }

    @Test
    void testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }));
    }

    @Test
    void testFromLittleEndianFromArrayOneArgUnsignedInt32() {
        assertEquals(VALUE_OF_4_BYTES, fromLittleEndian(LITTLE_ENDIAN_4_BYTES));
    }

    @Test
    void testFromLittleEndianFromArrayThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(ArrayUtils.EMPTY_BYTE_ARRAY, 0, LENGTH_EXCEEDING_LONG));
    }

    @Test
    void testFromLittleEndianFromArrayUnsignedInt32() {
        // Read the {2, 3, 4, 128} slice (offset 1, length 4), ignoring the leading 1.
        final byte[] bytes = { 1, 2, 3, 4, (byte) 128 };
        assertEquals(VALUE_OF_4_BYTES, fromLittleEndian(bytes, 1, 4));
    }

    @Test
    void testFromLittleEndianFromDataInput() throws IOException {
        final DataInput input = new DataInputStream(new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 }));
        // Only the first three bytes are consumed.
        assertEquals(VALUE_OF_3_BYTES, fromLittleEndian(input, 3));
    }

    @Test
    void testFromLittleEndianFromDataInputThrowsForLengthTooBig() {
        final DataInput input = new DataInputStream(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY));
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(input, LENGTH_EXCEEDING_LONG));
    }

    @Test
    void testFromLittleEndianFromDataInputThrowsForPrematureEnd() {
        // Only two bytes available, but three are requested.
        final DataInput input = new DataInputStream(new ByteArrayInputStream(new byte[] { 2, 3 }));
        assertThrows(EOFException.class, () -> fromLittleEndian(input, 3));
    }

    @Test
    void testFromLittleEndianFromDataInputUnsignedInt32() throws IOException {
        final DataInput input = new DataInputStream(new ByteArrayInputStream(LITTLE_ENDIAN_4_BYTES));
        assertEquals(VALUE_OF_4_BYTES, fromLittleEndian(input, 4));
    }

    @Test
    void testFromLittleEndianFromStream() throws IOException {
        final ByteArrayInputStream input = new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 });
        // Only the first three bytes are consumed.
        assertEquals(VALUE_OF_3_BYTES, fromLittleEndian(input, 3));
    }

    @Test
    void testFromLittleEndianFromStreamThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY), LENGTH_EXCEEDING_LONG));
    }

    @Test
    void testFromLittleEndianFromStreamThrowsForPrematureEnd() {
        // Only two bytes available, but three are requested.
        final ByteArrayInputStream input = new ByteArrayInputStream(new byte[] { 2, 3 });
        assertThrows(IOException.class, () -> fromLittleEndian(input, 3));
    }

    @Test
    void testFromLittleEndianFromStreamUnsignedInt32() throws IOException {
        final ByteArrayInputStream input = new ByteArrayInputStream(LITTLE_ENDIAN_4_BYTES);
        assertEquals(VALUE_OF_4_BYTES, fromLittleEndian(input, 4));
    }

    @Test
    void testFromLittleEndianFromSupplier() throws IOException {
        final ByteArrayInputStream input = new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 });
        // Only the first three bytes are consumed.
        assertEquals(VALUE_OF_3_BYTES, fromLittleEndian(new InputStreamByteSupplier(input), 3));
    }

    @Test
    void testFromLittleEndianFromSupplierThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(new InputStreamByteSupplier(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY)), LENGTH_EXCEEDING_LONG));
    }

    @Test
    void testFromLittleEndianFromSupplierThrowsForPrematureEnd() {
        // Only two bytes available, but three are requested.
        final ByteArrayInputStream input = new ByteArrayInputStream(new byte[] { 2, 3 });
        assertThrows(IOException.class, () -> fromLittleEndian(new InputStreamByteSupplier(input), 3));
    }

    @Test
    void testFromLittleEndianFromSupplierUnsignedInt32() throws IOException {
        final ByteArrayInputStream input = new ByteArrayInputStream(LITTLE_ENDIAN_4_BYTES);
        assertEquals(VALUE_OF_4_BYTES, fromLittleEndian(new InputStreamByteSupplier(input), 4));
    }

    @Test
    void testToLittleEndianToByteArray() {
        final byte[] target = new byte[4];
        // Write three bytes starting at offset 1; the byte at offset 0 stays zero.
        toLittleEndian(target, VALUE_OF_3_BYTES, 1, 3);
        assertArrayEquals(LITTLE_ENDIAN_3_BYTES, Arrays.copyOfRange(target, 1, 4));
    }

    @Test
    void testToLittleEndianToByteArrayUnsignedInt32() {
        final byte[] target = new byte[4];
        toLittleEndian(target, VALUE_OF_4_BYTES, 0, 4);
        assertArrayEquals(LITTLE_ENDIAN_4_BYTES, target);
    }

    @Test
    void testToLittleEndianToConsumer() throws IOException {
        final byte[] written;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(bos), VALUE_OF_3_BYTES, 3);
            written = bos.toByteArray();
            assertArrayEquals(LITTLE_ENDIAN_3_BYTES, written);
        }
        assertArrayEquals(LITTLE_ENDIAN_3_BYTES, written);
    }

    @Test
    void testToLittleEndianToConsumerUnsignedInt32() throws IOException {
        final byte[] written;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(bos), VALUE_OF_4_BYTES, 4);
            written = bos.toByteArray();
            assertArrayEquals(LITTLE_ENDIAN_4_BYTES, written);
        }
        assertArrayEquals(LITTLE_ENDIAN_4_BYTES, written);
    }

    @Test
    void testToLittleEndianToDataOutput() throws IOException {
        final byte[] written;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            final DataOutput output = new DataOutputStream(bos);
            toLittleEndian(output, VALUE_OF_3_BYTES, 3);
            written = bos.toByteArray();
            assertArrayEquals(LITTLE_ENDIAN_3_BYTES, written);
        }
        assertArrayEquals(LITTLE_ENDIAN_3_BYTES, written);
    }

    @Test
    void testToLittleEndianToDataOutputUnsignedInt32() throws IOException {
        final byte[] written;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            final DataOutput output = new DataOutputStream(bos);
            toLittleEndian(output, VALUE_OF_4_BYTES, 4);
            written = bos.toByteArray();
            assertArrayEquals(LITTLE_ENDIAN_4_BYTES, written);
        }
        assertArrayEquals(LITTLE_ENDIAN_4_BYTES, written);
    }

    @Test
    void testToLittleEndianToStream() throws IOException {
        final byte[] written;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(bos, VALUE_OF_3_BYTES, 3);
            written = bos.toByteArray();
            assertArrayEquals(LITTLE_ENDIAN_3_BYTES, written);
        }
        assertArrayEquals(LITTLE_ENDIAN_3_BYTES, written);
    }

    @Test
    void testToLittleEndianToStreamUnsignedInt32() throws IOException {
        final byte[] written;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(bos, VALUE_OF_4_BYTES, 4);
            written = bos.toByteArray();
            assertArrayEquals(LITTLE_ENDIAN_4_BYTES, written);
        }
        assertArrayEquals(LITTLE_ENDIAN_4_BYTES, written);
    }
}
