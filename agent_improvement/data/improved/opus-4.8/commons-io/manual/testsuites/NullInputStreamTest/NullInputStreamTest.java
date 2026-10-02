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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;

/**
 * Tests {@link NullInputStream}.
 */
class NullInputStreamTest {

    /**
     * A concrete {@link NullInputStream} that produces predictable, position-based data so the
     * tests can verify exactly which bytes are returned.
     * <p>
     * Each call to {@link #processByte()} returns the zero-based index of the byte that was just
     * read (because {@code getPosition()} has already been advanced past it), and
     * {@link #processBytes(byte[], int, int)} fills the array with the matching sequential values.
     * </p>
     */
    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size) {
            super(size);
        }

        TestNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        @Override
        protected int processByte() {
            // The position has already been incremented, so the byte just read is at index position - 1.
            return (int) getPosition() - 1;
        }

        @Override
        protected void processBytes(final byte[] bytes, final int offset, final int length) {
            // Fill the array so that each slot holds the absolute stream index of its byte.
            final int startPos = (int) getPosition() - length;
            for (int i = offset; i < length; i++) {
                bytes[i] = (byte) (startPos + i);
            }
        }

    }

    /** Use the same message as in java.io.InputStream.reset() in OpenJDK 8.0.275-1. */
    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

    @SuppressWarnings("resource")
    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testAvailableAfterClose(final int len) throws Exception {
        // Keep a reference to the stream so we can inspect it after the try-with-resources closes it.
        final InputStream shadow;
        try (InputStream in = new TestNullInputStream(len, false, false)) {
            assertEquals(len, in.available());
            shadow = in;
        }
        // A closed stream always reports zero available bytes.
        assertEquals(0, shadow.available());
    }

    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testAvailableAfterOpen(final int len) throws Exception {
        try (InputStream in = new TestNullInputStream(len, false, false)) {
            // A freshly opened stream reports its full emulated size as available.
            assertEquals(len, in.available());
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    void testDeprecatedSingleton() throws Exception {
        assertNotNull(NullInputStream.INSTANCE);
    }

    @Test
    void testEOFException() throws Exception {
        // Size 2, configured to throw an EOFException once the two bytes have been consumed.
        try (InputStream input = new TestNullInputStream(2, false, true)) {
            assertEquals(0, input.read(), "Read 1");
            assertEquals(1, input.read(), "Read 2");
            assertThrows(EOFException.class, () -> input.read());
        }
    }

    @Test
    void testMarkAndReset() throws Exception {
        int position = 0;
        final int readLimit = 10;
        try (InputStream input = new TestNullInputStream(100, true, false)) {

            assertTrue(input.markSupported(), "Mark Should be Supported");

            // Calling reset() before any mark() has been set is an error.
            final IOException noMarkException = assertThrows(IOException.class, input::reset);
            assertEquals("No position has been marked", noMarkException.getMessage(), "No Mark IOException message");

            // Read the first 3 bytes (positions 0, 1, 2) before marking.
            for (; position < 3; position++) {
                assertEquals(position, input.read(), "Read Before Mark [" + position + "]");
            }

            // Mark the current position (3) with a read limit of 10.
            input.mark(readLimit);

            // Read 3 more bytes past the mark (positions 3, 4, 5).
            for (int i = 0; i < 3; i++) {
                assertEquals(position + i, input.read(), "Read After Mark [" + i + "]");
            }

            // Reset back to the marked position (3).
            input.reset();

            // Re-read from the marked position. Reading readLimit + 1 bytes moves us past the read
            // limit, which invalidates the mark for the next reset().
            for (int i = 0; i < readLimit + 1; i++) {
                assertEquals(position + i, input.read(), "Read After Reset [" + i + "]");
            }

            // Resetting now fails because we have read beyond the mark's read limit.
            final IOException resetException = assertThrows(IOException.class, input::reset, "Read limit exceeded, expected IOException");
            assertEquals("Marked position [" + position + "] is no longer valid - passed the read limit [" + readLimit + "]", resetException.getMessage(),
                    "Read limit IOException message");
        }
    }

    @Test
    void testMarkNotSupported() throws Exception {
        // markSupported = false: both mark() and reset() must reject with UnsupportedOperationException.
        try (InputStream input = new TestNullInputStream(100, false, true)) {
            assertFalse(input.markSupported(), "Mark Should NOT be Supported");

            final UnsupportedOperationException markException = assertThrows(UnsupportedOperationException.class, () -> input.mark(5));
            assertEquals(MARK_RESET_NOT_SUPPORTED, markException.getMessage(), "mark() error message");

            final UnsupportedOperationException resetException = assertThrows(UnsupportedOperationException.class, input::reset);
            assertEquals(MARK_RESET_NOT_SUPPORTED, resetException.getMessage(), "reset() error message");
        }
    }

    @Test
    void testRead() throws Exception {
        final int size = 5;
        try (InputStream input = new TestNullInputStream(size)) {
            // Each read returns the byte's index; available() counts down as bytes are consumed.
            for (int i = 0; i < size; i++) {
                assertEquals(size - i, input.available(), "Check Size [" + i + "]");
                assertEquals(i, input.read(), "Check Value [" + i + "]");
            }
            assertEquals(0, input.available(), "Available after contents all read");

            // At end of file read() returns -1 (this stream does not throw) and nothing is available.
            assertEquals(-1, input.read(), "End of File");
            assertEquals(0, input.available(), "Available after End of File");

            // Reading again past EOF keeps returning -1.
            assertEquals(-1, input.read(), "End of File");

            // After closing, available() is still zero.
            input.close();
            assertEquals(0, input.available(), "Available after close");
        }
    }

    @Test
    void testReadAfterClose() throws Exception {
        try (InputStream in = new NullInputStream()) {
            assertEquals(0, in.available());
            in.close();
            // Reading from a closed stream throws IOException.
            assertThrows(IOException.class, in::read);
        }
    }

    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testReadAfterClose(final int len) throws Exception {
        try (InputStream in = new TestNullInputStream(len, false, false)) {
            assertEquals(len, in.available());
            in.close();
            // Reading from a closed stream throws IOException.
            assertThrows(IOException.class, in::read);
        }
    }

    @Test
    void testReadByteArray() throws Exception {
        final byte[] bytes = new byte[10];
        try (NullInputStream input = new TestNullInputStream(15)) {

            // First read fills the whole 10-byte array (stream has 15 bytes total).
            final int count1 = input.read(bytes);
            assertEquals(bytes.length, count1, "Read 1");
            for (int i = 0; i < count1; i++) {
                assertEquals(i, bytes[i], "Check Bytes 1");
            }

            // Second read returns only the remaining 5 bytes.
            final int count2 = input.read(bytes);
            assertEquals(5, count2, "Read 2");
            for (int i = 0; i < count2; i++) {
                assertEquals(count1 + i, bytes[i], "Check Bytes 2");
            }

            // Third read is at end of file: returns -1 (this stream does not throw).
            final int count3 = input.read(bytes);
            assertEquals(-1, count3, "Read 3 (EOF)");

            // Reading again past EOF keeps returning -1.
            final int count4 = input.read(bytes);
            assertEquals(-1, count4, "Read 4 (EOF)");

            // Re-initialize the stream to read from the beginning again.
            input.init();

            // Read into the array using an explicit offset and length.
            final int offset = 2;
            final int len = 4;
            final int count5 = input.read(bytes, offset, len);
            assertEquals(len, count5, "Read 5");
            for (int i = offset; i < len; i++) {
                assertEquals(i, bytes[i], "Check Bytes 2");
            }
        }
    }

    @Test
    void testReadByteArrayAfterClose() throws Exception {
        try (InputStream in = new NullInputStream()) {
            assertEquals(0, in.available());
            in.close();
            // Reading zero bytes is always allowed, even when closed.
            assertEquals(0, in.read(new byte[0]));
            // Reading a non-empty array from a closed stream throws IOException.
            assertThrows(IOException.class, () -> in.read(new byte[2]));
        }
    }

    @Test
    void testReadByteArrayIntIntAfterClose() throws Exception {
        try (InputStream in = new NullInputStream()) {
            assertEquals(0, in.available());
            in.close();
            // Bounds are validated before the closed check: length 1 into a zero-length array is out of bounds.
            assertThrows(IndexOutOfBoundsException.class, () -> in.read(new byte[0], 0, 1));
            // Reading zero bytes is always allowed, even when closed.
            assertEquals(0, in.read(new byte[1], 0, 0));
            // Reading a non-zero length from a closed stream throws IOException.
            assertThrows(IOException.class, () -> in.read(new byte[2], 0, 1));
        }
    }

    @Test
    void testReadByteArrayThrowAtEof() throws Exception {
        final byte[] bytes = new byte[10];
        // throwEofException = true: reading at EOF throws EOFException instead of returning -1.
        try (NullInputStream input = new TestNullInputStream(15, true, true)) {

            // First read fills the whole 10-byte array (stream has 15 bytes total).
            final int count1 = input.read(bytes);
            assertEquals(bytes.length, count1, "Read 1");
            for (int i = 0; i < count1; i++) {
                assertEquals(i, bytes[i], "Check Bytes 1");
            }

            // Second read returns only the remaining 5 bytes.
            final int count2 = input.read(bytes);
            assertEquals(5, count2, "Read 2");
            for (int i = 0; i < count2; i++) {
                assertEquals(count1 + i, bytes[i], "Check Bytes 2");
            }

            // At end of file the stream throws EOFException with a non-blank message.
            final IOException e1 = assertThrows(EOFException.class, () -> input.read(bytes));
            assertTrue(StringUtils.isNotBlank(e1.getMessage()));

            // Reading again past EOF keeps throwing.
            final IOException e2 = assertThrows(EOFException.class, () -> input.read(bytes));
            assertTrue(StringUtils.isNotBlank(e2.getMessage()));

            // Re-initialize the stream to read from the beginning again.
            input.init();

            // Read into the array using an explicit offset and length.
            final int offset = 2;
            final int len = 4;
            final int count5 = input.read(bytes, offset, len);
            assertEquals(len, count5, "Read 5");
            for (int i = offset; i < len; i++) {
                assertEquals(i, bytes[i], "Check Bytes 2");
            }
        }
    }

    @Test
    void testReadThrowAtEof() throws Exception {
        final int size = 5;
        // throwEofException = true: reading at EOF throws EOFException instead of returning -1.
        try (InputStream input = new TestNullInputStream(size, true, true)) {
            // Each read returns the byte's index; available() counts down as bytes are consumed.
            for (int i = 0; i < size; i++) {
                assertEquals(size - i, input.available(), "Check Size [" + i + "]");
                assertEquals(i, input.read(), "Check Value [" + i + "]");
            }
            assertEquals(0, input.available(), "Available after contents all read");

            // At end of file the stream throws EOFException with a non-blank message.
            final IOException e1 = assertThrows(EOFException.class, input::read);
            assertTrue(StringUtils.isNotBlank(e1.getMessage()));

            // Reading again past EOF keeps throwing.
            final IOException e2 = assertThrows(EOFException.class, input::read);
            assertTrue(StringUtils.isNotBlank(e2.getMessage()));

            // After closing, available() is still zero.
            input.close();
            assertEquals(0, input.available(), "Available after close");
        }
    }

    @Test
    void testSkip() throws Exception {
        // Size 10, does not throw at EOF (skip returns -1 once exhausted).
        try (InputStream input = new TestNullInputStream(10, true, false)) {
            assertEquals(0, input.read(), "Read 1");
            assertEquals(1, input.read(), "Read 2");
            assertEquals(5, input.skip(5), "Skip 1");
            assertEquals(7, input.read(), "Read 3");
            assertEquals(2, input.skip(5), "Skip 2"); // only 2 left to skip
            assertEquals(-1, input.skip(5), "Skip 3 (EOF)"); // End of file
            assertEquals(-1, input.skip(5), "Skip 3 (EOF)"); // End of file
        }
    }

    @Test
    void testSkipThrowAtEof() throws Exception {
        // Size 10, throwEofException = true (skip throws once exhausted).
        try (InputStream input = new TestNullInputStream(10, true, true)) {
            assertEquals(0, input.read(), "Read 1");
            assertEquals(1, input.read(), "Read 2");
            assertEquals(5, input.skip(5), "Skip 1");
            assertEquals(7, input.read(), "Read 3");
            assertEquals(2, input.skip(5), "Skip 2"); // only 2 left to skip

            // At end of file the stream throws EOFException with a non-blank message.
            final IOException e1 = assertThrows(EOFException.class, () -> input.skip(5), "Skip 3 (EOF)");
            assertTrue(StringUtils.isNotBlank(e1.getMessage()));

            // Skipping again past EOF keeps throwing.
            final IOException e2 = assertThrows(IOException.class, () -> input.skip(5), "Expected IOException for skipping after end of file");
            assertTrue(StringUtils.isNotBlank(e2.getMessage()));
        }
    }
}
