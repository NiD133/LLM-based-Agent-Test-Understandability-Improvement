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
package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CircularByteBuffer}.
 */
class CircularByteBufferTest {

    @Test
    void testAddByteSmallestBuffer() {
        final CircularByteBuffer buffer = new CircularByteBuffer(1);

        buffer.add((byte) 1);
        assertEquals(1, buffer.read());

        buffer.add((byte) 2);
        assertEquals(2, buffer.read());
    }

    @Test
    void testAddInvalidOffset() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        assertThrows(IllegalArgumentException.class, () -> buffer.add(new byte[] { 1, 2, 3 }, -1, 3));
    }

    @Test
    void testAddNegativeLength() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] sourceBytes = { 1, 2, 3 };

        assertThrows(IllegalArgumentException.class, () -> buffer.add(sourceBytes, 0, -1));
    }

    @Test
    void testAddNullBuffer() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        assertThrows(NullPointerException.class, () -> buffer.add(null, 0, 3));
    }

    @Test
    void testAddValidData() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final int bytesToAdd = 3;

        buffer.add(new byte[] { 3, 6, 9 }, 0, bytesToAdd);

        assertEquals(bytesToAdd, buffer.getCurrentNumberOfBytes());
    }

    @Test
    void testClear() {
        final byte[] sourceBytes = { 1, 2, 3 };
        final CircularByteBuffer buffer = new CircularByteBuffer(10);

        assertEquals(0, buffer.getCurrentNumberOfBytes());
        assertFalse(buffer.hasBytes());

        buffer.add(sourceBytes, 0, sourceBytes.length);

        assertEquals(3, buffer.getCurrentNumberOfBytes());
        assertEquals(7, buffer.getSpace());
        assertTrue(buffer.hasBytes());
        assertTrue(buffer.hasSpace());

        buffer.clear();

        assertEquals(0, buffer.getCurrentNumberOfBytes());
        assertEquals(10, buffer.getSpace());
        assertFalse(buffer.hasBytes());
        assertTrue(buffer.hasSpace());
    }

    @Test
    void testHasSpace() {
        final CircularByteBuffer buffer = new CircularByteBuffer(1);

        assertTrue(buffer.hasSpace());

        buffer.add((byte) 1);
        assertFalse(buffer.hasSpace());
        assertEquals(1, buffer.read());
        assertTrue(buffer.hasSpace());

        buffer.add((byte) 2);
        assertFalse(buffer.hasSpace());
        assertEquals(2, buffer.read());
        assertTrue(buffer.hasSpace());
    }

    @Test
    void testHasSpaceInt() {
        final CircularByteBuffer buffer = new CircularByteBuffer(1);

        assertTrue(buffer.hasSpace(1));

        buffer.add((byte) 1);
        assertFalse(buffer.hasSpace(1));
        assertEquals(1, buffer.read());
        assertTrue(buffer.hasSpace(1));

        buffer.add((byte) 2);
        assertFalse(buffer.hasSpace(1));
        assertEquals(2, buffer.read());
        assertTrue(buffer.hasSpace(1));
    }

    @Test
    void testPeekWithExcessiveLength() {
        assertFalse(new CircularByteBuffer().peek(new byte[] { 1, 3, 5, 7, 9 }, 0, 6));
    }

    @Test
    void testPeekWithInvalidOffset() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> buffer.peek(new byte[] { 2, 4, 6, 8, 10 }, -1, 5));

        assertEquals("Illegal offset: -1", exception.getMessage());
    }

    @Test
    void testPeekWithNegativeLength() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> buffer.peek(new byte[] { 1, 4, 3 }, 0, -1));

        assertEquals("Illegal length: -1", exception.getMessage());
    }

    @Test
    void testPeekWithValidArguments() {
        assertFalse(new CircularByteBuffer().peek(new byte[] { 5, 10, 15, 20, 25 }, 0, 5));
    }

    @Test
    void testReadByteArray() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final String sourceText = "0123456789";
        final byte[] sourceBytes = sourceText.getBytes(StandardCharsets.UTF_8);
        final byte[] destinationBytes = new byte[10];

        buffer.add(sourceBytes, 0, 10);
        buffer.read(destinationBytes, 0, 10);

        assertEquals(sourceText, new String(destinationBytes, StandardCharsets.UTF_8));
    }

    @Test
    void testReadByteArrayIllegalArgumentException() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] destinationBytes = new byte[10];

        assertThrows(IllegalArgumentException.class, () -> buffer.read(destinationBytes, -1, 10));
        assertThrows(IllegalArgumentException.class, () -> buffer.read(destinationBytes, 0, destinationBytes.length + 1));
    }
}
