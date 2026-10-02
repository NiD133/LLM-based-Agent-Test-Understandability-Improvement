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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link UnsynchronizedByteArrayInputStream}.
 */
class UnsynchronizedByteArrayInputStreamTest {

    /** Reusable 3-byte data sequence used across multiple read/skip/mark tests. */
    private static final byte[] THREE_BYTES = { (byte) 0xa, (byte) 0xb, (byte) 0xc };

    /** Convenience alias to avoid repeating the long field reference in assertions. */
    private static final byte[] EMPTY = IOUtils.EMPTY_BYTE_ARRAY;

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset, final int length) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).setLength(length).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    @DisplayName("available() equals the full buffer length when no offset is specified")
    void testAvailableEqualsBufferLength() throws IOException {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

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
    @DisplayName("available() reflects remaining bytes after the initial offset")
    @SuppressWarnings("resource") // not necessary to close these resources
    void testAvailableWithOffset() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        // Empty buffer: offset 0 => 0 bytes available; any positive offset => still 0
        UnsynchronizedByteArrayInputStream is = newStream(empty, 0);
        assertEquals(empty.length, is.available());
        is = newStream(empty, 1);
        assertEquals(0, is.available());

        // Single-byte buffer: offset past the end clamps available to 0
        is = newStream(one, 0);
        assertEquals(one.length, is.available());
        is = newStream(one, 1);
        assertEquals(0, is.available());
        is = newStream(one, 2);
        assertEquals(0, is.available());

        // 25-byte buffer: available = bufferLength - offset (clamped to 0 when offset >= length)
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
    @DisplayName("available() is capped by both the offset and the explicit length")
    @SuppressWarnings("resource") // not necessary to close these resources
    void testAvailableWithOffsetAndLength() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        // Empty buffer: available is always 0 regardless of offset/length
        UnsynchronizedByteArrayInputStream is = newStream(empty, 0);
        assertEquals(empty.length, is.available());
        is = newStream(empty, 1);
        assertEquals(0, is.available());
        is = newStream(empty, 0, 1);
        assertEquals(0, is.available());
        is = newStream(empty, 1, 1);
        assertEquals(0, is.available());

        // Single-byte buffer: available = min(length, bufferSize - offset), floored at 0
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

        // 25-byte buffer with various offset/length combinations
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
        // Requested length exceeding actual buffer size is clamped to the remaining bytes
        is = newStream(some, 0, some.length * 2);
        assertEquals(some.length, is.available());
        is = newStream(some, some.length - 1, 7);
        assertEquals(1, is.available());
    }

    @Test
    @DisplayName("builder rejects a negative offset")
    void testNegativeOffsetThrows() {
        assertThrows(IllegalArgumentException.class, () -> newStream(EMPTY, -1));
    }

    @Test
    @DisplayName("builder rejects a negative length")
    void testNegativeLengthThrows() {
        assertThrows(IllegalArgumentException.class, () -> newStream(EMPTY, 0, -1));
    }

    @Test
    @DisplayName("builder rejects a negative offset even when length is also supplied")
    void testNegativeOffsetWithLengthThrows() {
        assertThrows(IllegalArgumentException.class, () -> newStream(EMPTY, -1, 1));
    }

    @Test
    @DisplayName("read(buf, off, len) throws IndexOutOfBoundsException for negative len")
    @SuppressWarnings("resource") // not necessary to close these resources
    void testReadArrayWithNegativeLengthThrows() {
        final byte[] buf = IOUtils.EMPTY_BYTE_ARRAY;
        final UnsynchronizedByteArrayInputStream is = newStream(THREE_BYTES);
        assertThrows(IndexOutOfBoundsException.class, () -> is.read(buf, 0, -1));
    }

    @Test
    @DisplayName("read(buf, off, len) throws IndexOutOfBoundsException for negative off")
    void testReadArrayWithNegativeOffsetThrows() {
        final byte[] buf = IOUtils.EMPTY_BYTE_ARRAY;
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newStream(THREE_BYTES);
        assertThrows(IndexOutOfBoundsException.class, () -> is.read(buf, -1, 1));
    }

    @Test
    @DisplayName("read(buf, off, len) throws IndexOutOfBoundsException when off+len exceeds buf size")
    void testReadArrayWithRangeExceedingBufferThrows() {
        final byte[] buf = IOUtils.EMPTY_BYTE_ARRAY;
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newStream(THREE_BYTES);
        assertThrows(IndexOutOfBoundsException.class, () -> is.read(buf, 0, 1));
    }

    @Test
    @DisplayName("read(buf) throws NullPointerException when the destination buffer is null")
    void testReadArrayWithNullBufferThrows() {
        final byte[] buf = null;
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newStream(THREE_BYTES);
        assertThrows(NullPointerException.class, () -> is.read(buf));
    }

    @Test
    @DisplayName("skip() throws IllegalArgumentException for a negative skip count")
    void testSkipNegativeThrows() {
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newStream(THREE_BYTES);
        assertThrows(IllegalArgumentException.class, () -> is.skip(-1));
    }

    @Test
    @DisplayName("mark() and reset() allow re-reading from the marked position multiple times")
    void testMarkAndReset() {
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream is = newStream(THREE_BYTES);
        assertTrue(is.markSupported());
        assertEquals(0xa, is.read());
        assertTrue(is.markSupported());

        // Mark after reading the first byte; subsequent reads advance past the mark
        is.mark(10);
        assertEquals(0xb, is.read());
        assertEquals(0xc, is.read());

        // reset() repositions to the marked offset, allowing the same bytes to be re-read
        is.reset();
        assertEquals(0xb, is.read());
        assertEquals(0xc, is.read());
        assertEquals(END_OF_STREAM, is.read());

        // reset() can be called again to go back to the same mark a second time
        is.reset();
        assertEquals(0xb, is.read());
        assertEquals(0xc, is.read());
        assertEquals(END_OF_STREAM, is.read());
    }

    @Test
    @DisplayName("read(buf) reads available bytes into the buffer and returns the count")
    void testReadArray() {
        // Reading from an empty stream returns END_OF_STREAM and leaves the buffer untouched
        byte[] buf = new byte[10];
        UnsynchronizedByteArrayInputStream is = newStream(EMPTY);
        int read = is.read(buf);
        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], buf);

        // A zero-length destination buffer causes read() to return 0 without consuming data
        buf = IOUtils.EMPTY_BYTE_ARRAY;
        is = newStream(THREE_BYTES);
        read = is.read(buf);
        assertEquals(0, read);

        // Buffer larger than stream: all stream bytes are read; remaining buffer slots stay zero
        buf = new byte[10];
        is = newStream(THREE_BYTES);
        read = is.read(buf);
        assertEquals(3, read);
        assertEquals(0xa, buf[0]);
        assertEquals(0xb, buf[1]);
        assertEquals(0xc, buf[2]);
        assertEquals(0, buf[3]);

        // Buffer smaller than stream: first call fills the buffer, second call reads the remainder
        buf = new byte[2];
        is = newStream(THREE_BYTES);
        read = is.read(buf);
        assertEquals(2, read);
        assertEquals(0xa, buf[0]);
        assertEquals(0xb, buf[1]);
        read = is.read(buf);
        assertEquals(1, read);
        assertEquals(0xc, buf[0]);
    }

    @Test
    @DisplayName("read(buf, off, len) reads into a specific region of the destination buffer")
    void testReadArrayExplicit() {
        // Empty stream always returns END_OF_STREAM regardless of the requested region
        byte[] buf = new byte[10];
        UnsynchronizedByteArrayInputStream is = newStream(EMPTY);
        int read = is.read(buf, 0, 10);
        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], buf);

        buf = new byte[10];
        is = newStream(EMPTY);
        read = is.read(buf, 4, 2);
        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], buf);

        buf = new byte[10];
        is = newStream(EMPTY);
        read = is.read(buf, 4, 6);
        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], buf);

        // len=0 always returns 0 without consuming any bytes
        buf = IOUtils.EMPTY_BYTE_ARRAY;
        is = newStream(THREE_BYTES);
        read = is.read(buf, 0, 0);
        assertEquals(0, read);

        // Partial read of 2 bytes followed by a read that retrieves the single remaining byte
        buf = new byte[10];
        is = newStream(THREE_BYTES);
        read = is.read(buf, 0, 2);
        assertEquals(2, read);
        assertEquals(0xa, buf[0]);
        assertEquals(0xb, buf[1]);
        assertEquals(0, buf[2]);
        read = is.read(buf, 0, 10);
        assertEquals(1, read);
        assertEquals(0xc, buf[0]);
    }

    @Test
    @DisplayName("read() returns bytes one at a time in order, then END_OF_STREAM")
    void testReadSingle() {
        // Empty stream immediately signals end-of-stream
        UnsynchronizedByteArrayInputStream is = newStream(EMPTY);
        assertEquals(END_OF_STREAM, is.read());

        // Each successive call returns the next byte until the stream is exhausted
        is = newStream(THREE_BYTES);
        assertEquals(0xa, is.read());
        assertEquals(0xb, is.read());
        assertEquals(0xc, is.read());
        assertEquals(END_OF_STREAM, is.read());
    }

    @Test
    @DisplayName("skip() advances the read position; skipping past the end clamps to end-of-stream")
    void testSkip() {
        // Skip 1 leaves 2 bytes; next read returns the second byte
        UnsynchronizedByteArrayInputStream is = newStream(THREE_BYTES);
        assertEquals(3, is.available());
        is.skip(1);
        assertEquals(2, is.available());
        assertEquals(0xb, is.read());
        is.skip(1);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());

        // skip(0) is a no-op: position and available count are unchanged
        is = newStream(THREE_BYTES);
        assertEquals(3, is.available());
        is.skip(0);
        assertEquals(3, is.available());
        assertEquals(0xa, is.read());

        // Skip 2 leaves only the last byte readable
        is = newStream(THREE_BYTES);
        assertEquals(3, is.available());
        is.skip(2);
        assertEquals(1, is.available());
        assertEquals(0xc, is.read());
        assertEquals(END_OF_STREAM, is.read());

        // Skipping exactly the stream length exhausts it without error
        is = newStream(THREE_BYTES);
        assertEquals(3, is.available());
        is.skip(3);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());

        // Skipping more than available clamps to the end of the stream
        is = newStream(THREE_BYTES);
        assertEquals(3, is.available());
        is.skip(999);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());
    }
}
