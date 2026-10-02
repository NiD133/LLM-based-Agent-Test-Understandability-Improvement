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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CircularByteBuffer}.
 */
class CircularByteBufferTest {

    @Nested
    class AddTests {

        @Test
        void testAddSingleByteToSmallestBuffer() {
            // A capacity-1 buffer must support sequential add-then-read cycles without overflow
            final CircularByteBuffer buffer = new CircularByteBuffer(1);
            buffer.add((byte) 1);
            assertEquals(1, buffer.read());
            buffer.add((byte) 2);
            assertEquals(2, buffer.read());
        }

        @Test
        void testAddByteArrayWithNegativeOffsetThrowsException() {
            final CircularByteBuffer buffer = new CircularByteBuffer();
            assertThrows(IllegalArgumentException.class, () -> buffer.add(new byte[] { 1, 2, 3 }, -1, 3));
        }

        @Test
        void testAddByteArrayWithNegativeLengthThrowsException() {
            final CircularByteBuffer buffer = new CircularByteBuffer();
            final byte[] sourceData = { 1, 2, 3 };
            assertThrows(IllegalArgumentException.class, () -> buffer.add(sourceData, 0, -1));
        }

        @Test
        void testAddNullByteArrayThrowsException() {
            final CircularByteBuffer buffer = new CircularByteBuffer();
            assertThrows(NullPointerException.class, () -> buffer.add(null, 0, 3));
        }

        @Test
        void testAddByteArrayIncreasesCurrentByteCount() {
            final CircularByteBuffer buffer = new CircularByteBuffer();
            final int byteCount = 3;
            buffer.add(new byte[] { 3, 6, 9 }, 0, byteCount);
            assertEquals(byteCount, buffer.getCurrentNumberOfBytes());
        }
    }

    @Nested
    class ClearTests {

        @Test
        void testClearResetsBufferToEmpty() {
            final byte[] data = { 1, 2, 3 };
            final CircularByteBuffer buffer = new CircularByteBuffer(10);
            assertEquals(0, buffer.getCurrentNumberOfBytes());
            assertFalse(buffer.hasBytes());

            buffer.add(data, 0, data.length);
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
    }

    @Nested
    class HasSpaceTests {

        @Test
        void testHasSpaceTogglesCorrectlyAfterAddAndRead() {
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
        void testHasSpaceForCountTogglesCorrectlyAfterAddAndRead() {
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
    }

    @Nested
    class PeekTests {

        @Test
        void testPeekReturnsFalseWhenRequestedLengthExceedsBufferContents() {
            // Empty buffer cannot satisfy a peek for 6 bytes; returns false without exception
            assertFalse(new CircularByteBuffer().peek(new byte[] { 1, 3, 5, 7, 9 }, 0, 6));
        }

        @Test
        void testPeekWithNegativeOffsetThrowsException() {
            final CircularByteBuffer buffer = new CircularByteBuffer();
            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> buffer.peek(new byte[] { 2, 4, 6, 8, 10 }, -1, 5));
            assertEquals("Illegal offset: -1", e.getMessage());
        }

        @Test
        void testPeekWithNegativeLengthThrowsException() {
            final CircularByteBuffer buffer = new CircularByteBuffer();
            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> buffer.peek(new byte[] { 1, 4, 3 }, 0, -1));
            assertEquals("Illegal length: -1", e.getMessage());
        }

        @Test
        void testPeekReturnsFalseOnEmptyBuffer() {
            // An empty buffer cannot match any byte pattern
            assertFalse(new CircularByteBuffer().peek(new byte[] { 5, 10, 15, 20, 25 }, 0, 5));
        }
    }

    @Nested
    class ReadTests {

        @Test
        void testReadByteArrayReturnsAddedData() {
            final CircularByteBuffer buffer = new CircularByteBuffer();
            final String expectedString = "0123456789";
            final byte[] bytesToAdd = expectedString.getBytes(StandardCharsets.UTF_8);
            buffer.add(bytesToAdd, 0, 10);
            final byte[] readBytes = new byte[10];
            buffer.read(readBytes, 0, 10);
            assertEquals(expectedString, new String(readBytes, StandardCharsets.UTF_8));
        }

        @Test
        void testReadByteArrayWithInvalidArgumentsThrowsException() {
            final CircularByteBuffer buffer = new CircularByteBuffer();
            final byte[] outputBuffer = new byte[10];
            // targetOffset < 0
            assertThrows(IllegalArgumentException.class, () -> buffer.read(outputBuffer, -1, 10));
            // length exceeds the output buffer capacity
            assertThrows(IllegalArgumentException.class, () -> buffer.read(outputBuffer, 0, outputBuffer.length + 1));
        }
    }
}
