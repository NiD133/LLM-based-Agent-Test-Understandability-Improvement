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
package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Basic unit tests for the alternative ByteArrayInputStream implementation.
 */
class UnsynchronizedByteArrayInputStreamTest {

    /** The three distinct byte values used as sample stream content throughout these tests. */
    private static final byte BYTE_A = 0xa;
    private static final byte BYTE_B = 0xb;
    private static final byte BYTE_C = 0xc;

    /**
     * Builds a stream over the given buffer, defaulting offset and length to cover the whole buffer.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Builds a stream over the given buffer starting at {@code offset}.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Builds a stream over the given buffer starting at {@code offset} and spanning {@code length} bytes.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset, final int length) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).setLength(length).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Builds a stream over the three sample bytes {@code [A, B, C]}.
     */
    private UnsynchronizedByteArrayInputStream newAbcStream() {
        return newStream(new byte[] { BYTE_A, BYTE_B, BYTE_C });
    }

    @Test
    void testConstructor1() throws IOException {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        // With no offset, the whole buffer is available.
        try (UnsynchronizedByteArrayInputStream is = newStream(empty)) {
            assertEquals(empty.length, is.available());
        }
        try (UnsynchronizedByteArrayInputStream is = newStream(one)) {
            assertEquals(one.length, is.available());
        }
        try (UnsynchronizedByteArrayInputStream is = newStream(some)) {
            assertEquals(some.length, is.available());
        }
    }

    @Test
    @SuppressWarnings("resource") // not necessary to close these resources
    void testConstructor2() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        // Empty buffer: available bytes are clamped to 0 regardless of offset.
        UnsynchronizedByteArrayInputStream is = newStream(empty, 0);
        assertEquals(empty.length, is.available());
        is = newStream(empty, 1);
        assertEquals(0, is.available());

        // Single-byte buffer: only offset 0 leaves a byte available.
        is = newStream(one, 0);
        assertEquals(one.length, is.available());
        is = newStream(one, 1);
        assertEquals(0, is.available());
        is = newStream(one, 2);
        assertEquals(0, is.available());

        // 25-byte buffer: available bytes shrink as the offset advances.
        is = newStream(some, 0);
        assertEquals(some.length, is.available());
        is = newStream(some, 1);
        assertEquals(some.length - 1, is.available());
        is = newStream(some, 10);
        assertEquals(some.length - 10, is.available());
        is = newStream(some, some.length);
        assertEquals(0, is.available());
    }

    @Test
    @SuppressWarnings("resource") // not necessary to close these resources
    void testConstructor3() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        // Empty buffer: never any bytes available, whatever the offset/length.
        UnsynchronizedByteArrayInputStream is = newStream(empty, 0);
        assertEquals(empty.length, is.available());
        is = newStream(empty, 1);
        assertEquals(0, is.available());
        is = newStream(empty, 0, 1);
        assertEquals(0, is.available());
        is = newStream(empty, 1, 1);
        assertEquals(0, is.available());

        // Single-byte buffer with various offset/length combinations.
        is = newStream(one, 0);
        assertEquals(one.length, is.available());
        is = newStream(one, 1);
        assertEquals(one.length - 1, is.available());
        is = newStream(one, 2);
        assertEquals(0, is.available());
        is = newStream(one, 0, 1);
        assertEquals(1, is.available());
        is = newStream(one, 1, 1);
        assertEquals(0, is.available());
        is = newStream(one, 0, 2);
        assertEquals(1, is.available());
        is = newStream(one, 2, 1);
        assertEquals(0, is.available());
        is = newStream(one, 2, 2);
        assertEquals(0, is.available());

        // 25-byte buffer: available bytes are bounded by both the length and the end of the buffer.
        is = newStream(some, 0);
        assertEquals(some.length, is.available());
        is = newStream(some, 1);
        assertEquals(some.length - 1, is.available());
        is = newStream(some, 10);
        assertEquals(some.length - 10, is.available());
        is = newStream(some, some.length);
        assertEquals(0, is.available());
        is = newStream(some, some.length, some.length);
        assertEquals(0, is.available());
        is = newStream(some, some.length - 1, some.length);
        assertEquals(1, is.available());
        is = newStream(some, 0, 7);
        assertEquals(7, is.available());
        is = newStream(some, 7, 7);
        assertEquals(7, is.available());
        is = newStream(some, 0, some.length * 2);
        assertEquals(some.length, is.available());
        is = newStream(some, some.length - 1, 7);
        assertEquals(1, is.available());
    }

    @Test
    void testInvalidConstructor2OffsetUnder() {
        assertThrows(IllegalArgumentException.class, () -> newStream(IOUtils.EMPTY_BYTE_ARRAY, -1));
    }

    @Test
    void testInvalidConstructor3LengthUnder() {
        assertThrows(IllegalArgumentException.class, () -> newStream(IOUtils.EMPTY_BYTE_ARRAY, 0, -1));
    }

    @Test
    void testInvalidConstructor3OffsetUnder() {
        assertThrows(IllegalArgumentException.class, () -> newStream(IOUtils.EMPTY_BYTE_ARRAY, -1, 1));
    }

    @Test
    @SuppressWarnings("resource") // not necessary to close these resources
    void testInvalidReadArrayExplicitLenUnder() {
        final byte[] dest = IOUtils.EMPTY_BYTE_ARRAY;
        final UnsynchronizedByteArrayInputStream is = newAbcStream();
        // A negative length is rejected.
        assertThrows(IndexOutOfBoundsException.class, () -> is.read(dest, 0, -1));
    }

    @Test
    void testInvalidReadArrayExplicitOffsetUnder() {
        final byte[] dest = IOUtils.EMPTY_BYTE_ARRAY;
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newAbcStream();
        // A negative destination offset is rejected.
        assertThrows(IndexOutOfBoundsException.class, () -> is.read(dest, -1, 1));
    }

    @Test
    void testInvalidReadArrayExplicitRangeOver() {
        final byte[] dest = IOUtils.EMPTY_BYTE_ARRAY;
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newAbcStream();
        // Reading one byte into a zero-length destination is out of range.
        assertThrows(IndexOutOfBoundsException.class, () -> is.read(dest, 0, 1));
    }

    @Test
    void testInvalidReadArrayNull() {
        final byte[] dest = null;
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newAbcStream();
        assertThrows(NullPointerException.class, () -> is.read(dest));
    }

    @Test
    void testInvalidSkipNUnder() {
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newAbcStream();
        // Skipping backward is not supported.
        assertThrows(IllegalArgumentException.class, () -> is.skip(-1));
    }

    @Test
    void testMarkReset() {
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newAbcStream();
        assertTrue(is.markSupported());
        assertEquals(BYTE_A, is.read());
        assertTrue(is.markSupported());

        // Mark right after reading 'A', so reset() returns to the 'B' position.
        is.mark(10);

        assertEquals(BYTE_B, is.read());
        assertEquals(BYTE_C, is.read());

        is.reset();

        assertEquals(BYTE_B, is.read());
        assertEquals(BYTE_C, is.read());
        assertEquals(END_OF_STREAM, is.read());

        // The mark is not consumed: a second reset() rewinds to the same position.
        is.reset();

        assertEquals(BYTE_B, is.read());
        assertEquals(BYTE_C, is.read());
        assertEquals(END_OF_STREAM, is.read());
    }

    @Test
    void testReadArray() {
        // Reading from an empty stream reports end-of-stream and leaves the destination untouched.
        byte[] dest = new byte[10];
        UnsynchronizedByteArrayInputStream is = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        int read = is.read(dest);
        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], dest);

        // A zero-length destination reads nothing.
        dest = IOUtils.EMPTY_BYTE_ARRAY;
        is = newAbcStream();
        read = is.read(dest);
        assertEquals(0, read);

        // A roomy destination receives all three bytes; the rest stays zero.
        dest = new byte[10];
        is = newAbcStream();
        read = is.read(dest);
        assertEquals(3, read);
        assertEquals(BYTE_A, dest[0]);
        assertEquals(BYTE_B, dest[1]);
        assertEquals(BYTE_C, dest[2]);
        assertEquals(0, dest[3]);

        // A small destination fills up, then the next read drains the remainder.
        dest = new byte[2];
        is = newAbcStream();
        read = is.read(dest);
        assertEquals(2, read);
        assertEquals(BYTE_A, dest[0]);
        assertEquals(BYTE_B, dest[1]);
        read = is.read(dest);
        assertEquals(1, read);
        assertEquals(BYTE_C, dest[0]);
    }

    @Test
    void testReadArrayExplicit() {
        // Empty stream reports end-of-stream for any requested range.
        byte[] dest = new byte[10];
        UnsynchronizedByteArrayInputStream is = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        int read = is.read(dest, 0, 10);
        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], dest);

        dest = new byte[10];
        is = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        read = is.read(dest, 4, 2);
        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], dest);

        dest = new byte[10];
        is = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        read = is.read(dest, 4, 6);
        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], dest);

        // Requesting zero bytes reads nothing, even with data available.
        dest = IOUtils.EMPTY_BYTE_ARRAY;
        is = newAbcStream();
        read = is.read(dest, 0, 0);
        assertEquals(0, read);

        // Read the first two bytes, then drain the remaining byte.
        dest = new byte[10];
        is = newAbcStream();
        read = is.read(dest, 0, 2);
        assertEquals(2, read);
        assertEquals(BYTE_A, dest[0]);
        assertEquals(BYTE_B, dest[1]);
        assertEquals(0, dest[2]);
        read = is.read(dest, 0, 10);
        assertEquals(1, read);
        assertEquals(BYTE_C, dest[0]);
    }

    @Test
    void testReadSingle() {
        // An empty stream immediately returns end-of-stream.
        UnsynchronizedByteArrayInputStream is = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        assertEquals(END_OF_STREAM, is.read());

        // Bytes are returned in order, followed by end-of-stream.
        is = newAbcStream();
        assertEquals(BYTE_A, is.read());
        assertEquals(BYTE_B, is.read());
        assertEquals(BYTE_C, is.read());
        assertEquals(END_OF_STREAM, is.read());
    }

    @Test
    void testSkip() {
        // Skip one byte, then read the next.
        UnsynchronizedByteArrayInputStream is = newAbcStream();
        assertEquals(3, is.available());

        is.skip(1);
        assertEquals(2, is.available());
        assertEquals(BYTE_B, is.read());

        is.skip(1);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());

        // Skipping zero leaves the position unchanged.
        is = newAbcStream();
        assertEquals(3, is.available());
        is.skip(0);
        assertEquals(3, is.available());
        assertEquals(BYTE_A, is.read());

        // Skip two bytes, leaving only the last one.
        is = newAbcStream();
        assertEquals(3, is.available());
        is.skip(2);
        assertEquals(1, is.available());
        assertEquals(BYTE_C, is.read());
        assertEquals(END_OF_STREAM, is.read());

        // Skipping exactly the whole stream exhausts it.
        is = newAbcStream();
        assertEquals(3, is.available());
        is.skip(3);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());

        // Skipping past the end also exhausts the stream.
        is = newAbcStream();
        assertEquals(3, is.available());
        is.skip(999);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());
    }
}
