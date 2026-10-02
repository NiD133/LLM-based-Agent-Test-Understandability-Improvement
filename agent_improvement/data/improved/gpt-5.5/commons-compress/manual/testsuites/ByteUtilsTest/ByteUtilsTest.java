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

    private static final byte[] THREE_BYTES = { 2, 3, 4 };
    private static final byte[] FOUR_BYTES_WITH_SIGN_BIT = { 2, 3, 4, (byte) 128 };
    private static final byte[] TOO_SHORT_FOR_THREE_BYTE_VALUE = { 2, 3 };
    private static final byte[] OFFSET_THREE_BYTE_VALUE = { 1, 2, 3, 4, 5 };
    private static final byte[] OFFSET_FOUR_BYTE_VALUE = { 1, 2, 3, 4, (byte) 128 };

    private static final long THREE_BYTE_VALUE = 2 + 3 * 256 + 4 * 256 * 256;
    private static final long UNSIGNED_INT_32_VALUE = THREE_BYTE_VALUE + 128L * 256 * 256 * 256;

    @Test
    void testFromLittleEndianFromArray() {
        assertEquals(THREE_BYTE_VALUE, fromLittleEndian(OFFSET_THREE_BYTE_VALUE, 1, 3));
    }

    @Test
    void testFromLittleEndianFromArrayOneArg() {
        assertEquals(THREE_BYTE_VALUE, fromLittleEndian(THREE_BYTES));
    }

    @Test
    void testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }));
    }

    @Test
    void testFromLittleEndianFromArrayOneArgUnsignedInt32() {
        assertEquals(UNSIGNED_INT_32_VALUE, fromLittleEndian(FOUR_BYTES_WITH_SIGN_BIT));
    }

    @Test
    void testFromLittleEndianFromArrayThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(ArrayUtils.EMPTY_BYTE_ARRAY, 0, 9));
    }

    @Test
    void testFromLittleEndianFromArrayUnsignedInt32() {
        assertEquals(UNSIGNED_INT_32_VALUE, fromLittleEndian(OFFSET_FOUR_BYTE_VALUE, 1, 4));
    }

    @Test
    void testFromLittleEndianFromDataInput() throws IOException {
        final DataInput din = dataInput(new byte[] { 2, 3, 4, 5 });
        assertEquals(THREE_BYTE_VALUE, fromLittleEndian(din, 3));
    }

    @Test
    void testFromLittleEndianFromDataInputThrowsForLengthTooBig() {
        final DataInput din = new DataInputStream(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY));
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(din, 9));
    }

    @Test
    void testFromLittleEndianFromDataInputThrowsForPrematureEnd() {
        final DataInput din = dataInput(TOO_SHORT_FOR_THREE_BYTE_VALUE);
        assertThrows(EOFException.class, () -> fromLittleEndian(din, 3));
    }

    @Test
    void testFromLittleEndianFromDataInputUnsignedInt32() throws IOException {
        final DataInput din = dataInput(FOUR_BYTES_WITH_SIGN_BIT);
        assertEquals(UNSIGNED_INT_32_VALUE, fromLittleEndian(din, 4));
    }

    @Test
    void testFromLittleEndianFromStream() throws IOException {
        final ByteArrayInputStream bin = inputStream(2, 3, 4, 5);
        assertEquals(THREE_BYTE_VALUE, fromLittleEndian(bin, 3));
    }

    @Test
    void testFromLittleEndianFromStreamThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY), 9));
    }

    @Test
    void testFromLittleEndianFromStreamThrowsForPrematureEnd() {
        final ByteArrayInputStream bin = inputStream(TOO_SHORT_FOR_THREE_BYTE_VALUE);
        assertThrows(IOException.class, () -> fromLittleEndian(bin, 3));
    }

    @Test
    void testFromLittleEndianFromStreamUnsignedInt32() throws IOException {
        final ByteArrayInputStream bin = inputStream(FOUR_BYTES_WITH_SIGN_BIT);
        assertEquals(UNSIGNED_INT_32_VALUE, fromLittleEndian(bin, 4));
    }

    @Test
    void testFromLittleEndianFromSupplier() throws IOException {
        final ByteArrayInputStream bin = inputStream(2, 3, 4, 5);
        assertEquals(THREE_BYTE_VALUE, fromLittleEndian(new InputStreamByteSupplier(bin), 3));
    }

    @Test
    void testFromLittleEndianFromSupplierThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(new InputStreamByteSupplier(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY)), 9));
    }

    @Test
    void testFromLittleEndianFromSupplierThrowsForPrematureEnd() {
        final ByteArrayInputStream bin = inputStream(TOO_SHORT_FOR_THREE_BYTE_VALUE);
        assertThrows(IOException.class, () -> fromLittleEndian(new InputStreamByteSupplier(bin), 3));
    }

    @Test
    void testFromLittleEndianFromSupplierUnsignedInt32() throws IOException {
        final ByteArrayInputStream bin = inputStream(FOUR_BYTES_WITH_SIGN_BIT);
        assertEquals(UNSIGNED_INT_32_VALUE, fromLittleEndian(new InputStreamByteSupplier(bin), 4));
    }

    @Test
    void testToLittleEndianToByteArray() {
        final byte[] b = new byte[4];
        toLittleEndian(b, THREE_BYTE_VALUE, 1, 3);
        assertArrayEquals(THREE_BYTES, Arrays.copyOfRange(b, 1, 4));
    }

    @Test
    void testToLittleEndianToByteArrayUnsignedInt32() {
        final byte[] b = new byte[4];
        toLittleEndian(b, UNSIGNED_INT_32_VALUE, 0, 4);
        assertArrayEquals(FOUR_BYTES_WITH_SIGN_BIT, b);
    }

    @Test
    void testToLittleEndianToConsumer() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(bos), THREE_BYTE_VALUE, 3);
            byteArray = bos.toByteArray();
            assertArrayEquals(THREE_BYTES, byteArray);
        }
        assertArrayEquals(THREE_BYTES, byteArray);
    }

    @Test
    void testToLittleEndianToConsumerUnsignedInt32() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(bos), UNSIGNED_INT_32_VALUE, 4);
            byteArray = bos.toByteArray();
            assertArrayEquals(FOUR_BYTES_WITH_SIGN_BIT, byteArray);
        }
        assertArrayEquals(FOUR_BYTES_WITH_SIGN_BIT, byteArray);
    }

    @Test
    void testToLittleEndianToDataOutput() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            final DataOutput dos = new DataOutputStream(bos);
            toLittleEndian(dos, THREE_BYTE_VALUE, 3);
            byteArray = bos.toByteArray();
            assertArrayEquals(THREE_BYTES, byteArray);
        }
        assertArrayEquals(THREE_BYTES, byteArray);
    }

    @Test
    void testToLittleEndianToDataOutputUnsignedInt32() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            final DataOutput dos = new DataOutputStream(bos);
            toLittleEndian(dos, UNSIGNED_INT_32_VALUE, 4);
            byteArray = bos.toByteArray();
            assertArrayEquals(FOUR_BYTES_WITH_SIGN_BIT, byteArray);
        }
        assertArrayEquals(FOUR_BYTES_WITH_SIGN_BIT, byteArray);
    }

    @Test
    void testToLittleEndianToStream() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(bos, THREE_BYTE_VALUE, 3);
            byteArray = bos.toByteArray();
            assertArrayEquals(THREE_BYTES, byteArray);
        }
        assertArrayEquals(THREE_BYTES, byteArray);
    }

    @Test
    void testToLittleEndianToStreamUnsignedInt32() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(bos, UNSIGNED_INT_32_VALUE, 4);
            byteArray = bos.toByteArray();
            assertArrayEquals(FOUR_BYTES_WITH_SIGN_BIT, byteArray);
        }
        assertArrayEquals(FOUR_BYTES_WITH_SIGN_BIT, byteArray);
    }

    private static DataInput dataInput(final byte... bytes) {
        return new DataInputStream(inputStream(bytes));
    }

    private static ByteArrayInputStream inputStream(final int... bytes) {
        final byte[] byteArray = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byteArray[i] = (byte) bytes[i];
        }
        return inputStream(byteArray);
    }

    private static ByteArrayInputStream inputStream(final byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }
}
