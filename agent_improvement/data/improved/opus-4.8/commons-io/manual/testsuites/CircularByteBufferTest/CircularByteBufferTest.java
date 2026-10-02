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

    // ------------------------------------------------------------------
    // add(byte)
    // ------------------------------------------------------------------

    @Test
    void testAddByteSmallestBuffer() {
        // A buffer of size 1 can only hold one byte at a time, so each added
        // byte must be read back out before the next one can be added.
        final CircularByteBuffer buffer = new CircularByteBuffer(1);

        buffer.add((byte) 1);
        assertEquals(1, buffer.read());

        buffer.add((byte) 2);
        assertEquals(2, buffer.read());
    }

    // ------------------------------------------------------------------
    // add(byte[], int, int)
    // ------------------------------------------------------------------

    @Test
    void testAddValidData() {
        // Adding 3 bytes should leave exactly 3 bytes in the buffer.
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] data = { 3, 6, 9 };

        buffer.add(data, 0, data.length);

        assertEquals(data.length, buffer.getCurrentNumberOfBytes());
    }

    @Test
    void testAddInvalidOffset() {
        // A negative offset is rejected.
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] data = { 1, 2, 3 };

        assertThrows(IllegalArgumentException.class, () -> buffer.add(data, -1, 3));
    }

    @Test
    void testAddNegativeLength() {
        // A negative length is rejected.
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] data = { 1, 2, 3 };

        assertThrows(IllegalArgumentException.class, () -> buffer.add(data, 0, -1));
    }

    @Test
    void testAddNullBuffer() {
        // A null source array is rejected.
        final CircularByteBuffer buffer = new CircularByteBuffer();

        assertThrows(NullPointerException.class, () -> buffer.add(null, 0, 3));
    }

    // ------------------------------------------------------------------
    // clear()
    // ------------------------------------------------------------------

    @Test
    void testClear() {
        final byte[] data = { 1, 2, 3 };
        final CircularByteBuffer buffer = new CircularByteBuffer(10);

        // A fresh buffer is empty.
        assertEquals(0, buffer.getCurrentNumberOfBytes());
        assertFalse(buffer.hasBytes());

        // After adding 3 of the 10 available bytes, 7 bytes of space remain.
        buffer.add(data, 0, data.length);
        assertEquals(3, buffer.getCurrentNumberOfBytes());
        assertEquals(7, buffer.getSpace());
        assertTrue(buffer.hasBytes());
        assertTrue(buffer.hasSpace());

        // clear() resets the buffer back to its empty state.
        buffer.clear();
        assertEquals(0, buffer.getCurrentNumberOfBytes());
        assertEquals(10, buffer.getSpace());
        assertFalse(buffer.hasBytes());
        assertTrue(buffer.hasSpace());
    }

    // ------------------------------------------------------------------
    // hasSpace() / hasSpace(int)
    // ------------------------------------------------------------------

    @Test
    void testHasSpace() {
        // With a size-1 buffer, space toggles as the single slot is filled and emptied.
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
        // Same toggling behaviour as testHasSpace(), but asking for room for 1 byte explicitly.
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

    // ------------------------------------------------------------------
    // peek(byte[], int, int)
    // ------------------------------------------------------------------

    @Test
    void testPeekWithValidArguments() {
        // An empty buffer never matches a non-empty peek request.
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] expected = { 5, 10, 15, 20, 25 };

        assertFalse(buffer.peek(expected, 0, 5));
    }

    @Test
    void testPeekWithExcessiveLength() {
        // A length that exceeds the source array's bounds yields no match.
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] expected = { 1, 3, 5, 7, 9 };

        assertFalse(buffer.peek(expected, 0, 6));
    }

    @Test
    void testPeekWithInvalidOffset() {
        // A negative offset is rejected, reporting the offending value.
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] expected = { 2, 4, 6, 8, 10 };

        final IllegalArgumentException e =
                assertThrows(IllegalArgumentException.class, () -> buffer.peek(expected, -1, 5));
        assertEquals("Illegal offset: -1", e.getMessage());
    }

    @Test
    void testPeekWithNegativeLength() {
        // A negative length is rejected, reporting the offending value.
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] expected = { 1, 4, 3 };

        final IllegalArgumentException e =
                assertThrows(IllegalArgumentException.class, () -> buffer.peek(expected, 0, -1));
        assertEquals("Illegal length: -1", e.getMessage());
    }

    // ------------------------------------------------------------------
    // read(byte[], int, int)
    // ------------------------------------------------------------------

    @Test
    void testReadByteArray() {
        // Bytes read back out should match exactly what was written in.
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final String text = "0123456789";
        final byte[] bytesIn = text.getBytes(StandardCharsets.UTF_8);
        buffer.add(bytesIn, 0, bytesIn.length);

        final byte[] bytesOut = new byte[10];
        buffer.read(bytesOut, 0, 10);

        assertEquals(text, new String(bytesOut, StandardCharsets.UTF_8));
    }

    @Test
    void testReadByteArrayIllegalArgumentException() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] bytesOut = new byte[10];

        // A negative target offset is rejected.
        assertThrows(IllegalArgumentException.class, () -> buffer.read(bytesOut, -1, 10));
        // A length that overruns the target array is rejected.
        assertThrows(IllegalArgumentException.class, () -> buffer.read(bytesOut, 0, bytesOut.length + 1));
    }
}
