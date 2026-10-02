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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link NullReader}.
 */
class NullReaderTest {

    /**
     * A {@link NullReader} subclass that fills in real character values so the
     * tests can assert on the exact bytes produced.
     * <p>
     * Both overrides derive each character from the reader's current position:
     * the character at position {@code p} is the value {@code p} itself. This
     * makes the expected values trivially predictable in the assertions below
     * (position 0 yields 0, position 1 yields 1, and so on).
     * </p>
     */
    private static final class TestNullReader extends NullReader {

        TestNullReader(final int size) {
            super(size);
        }

        TestNullReader(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        /** Returns the position that was just consumed (position is already incremented when this runs). */
        @Override
        protected int processChar() {
            return (int) getPosition() - 1;
        }

        /** Fills {@code chars} so that the character at each index equals its absolute reader position. */
        @Override
        protected void processChars(final char[] chars, final int offset, final int length) {
            final int startPos = (int) getPosition() - length;
            for (int i = offset; i < length; i++) {
                chars[i] = (char) (startPos + i);
            }
        }

    }

    // Use the same message as in java.io.InputStream.reset() in OpenJDK 8.0.275-1.
    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

    private static final String READ_AFTER_EOF = "Read after end of file";

    @Test
    void testEOFException() throws Exception {
        // A reader of size 2 configured to throw EOFException at end of file.
        try (Reader reader = new TestNullReader(2, false, true)) {
            assertEquals(0, reader.read(), "Read 1");
            assertEquals(1, reader.read(), "Read 2");
            // The third read reaches the end of file and throws instead of returning -1.
            assertThrows(EOFException.class, reader::read);
        }
    }

    @Test
    void testMarkAndReset() throws Exception {
        final int readLimit = 10;
        try (Reader reader = new TestNullReader(100, true, false)) {

            assertTrue(reader.markSupported(), "Mark Should be Supported");

            // reset() before any mark() fails.
            final IOException resetException = assertThrows(IOException.class, reader::reset);
            assertEquals("No position has been marked", resetException.getMessage(), "No Mark IOException message");

            // Read positions 0, 1, 2 before marking.
            int position = 0;
            for (; position < 3; position++) {
                assertEquals(position, reader.read(), "Read Before Mark [" + position + "]");
            }

            // Mark at position 3.
            reader.mark(readLimit);

            // Read 3 more characters past the mark (positions 3, 4, 5).
            for (int i = 0; i < 3; i++) {
                assertEquals(position + i, reader.read(), "Read After Mark [" + i + "]");
            }

            // Reset jumps back to the marked position (3).
            reader.reset();

            // Re-read from the marked position; reading readLimit + 1 characters
            // moves the position past the allowed read limit.
            for (int i = 0; i < readLimit + 1; i++) {
                assertEquals(position + i, reader.read(), "Read After Reset [" + i + "]");
            }

            // reset() now fails because the read limit has been exceeded.
            final IOException e = assertThrows(IOException.class, reader::reset);
            assertEquals("Marked position [" + position + "] is no longer valid - passed the read limit [" + readLimit + "]", e.getMessage(),
                    "Read limit IOException message");
        }
    }

    @Test
    void testMarkNotSupported() throws Exception {
        // mark/reset are disabled for this reader.
        try (Reader reader = new TestNullReader(100, false, true)) {
            assertFalse(reader.markSupported(), "Mark Should NOT be Supported");

            final UnsupportedOperationException markException =
                    assertThrows(UnsupportedOperationException.class, () -> reader.mark(5));
            assertEquals(MARK_RESET_NOT_SUPPORTED, markException.getMessage(), "mark() error message");

            final UnsupportedOperationException resetException =
                    assertThrows(UnsupportedOperationException.class, reader::reset);
            assertEquals(MARK_RESET_NOT_SUPPORTED, resetException.getMessage(), "reset() error message");
        }
    }

    @Test
    void testRead() throws Exception {
        final int size = 5;
        final TestNullReader reader = new TestNullReader(size);

        // Each read returns its position value: 0, 1, 2, 3, 4.
        for (int i = 0; i < size; i++) {
            assertEquals(i, reader.read(), "Check Value [" + i + "]");
        }

        // The next read reports end of file (-1, since this reader does not throw).
        assertEquals(-1, reader.read(), "End of File");

        // Reading again, now past the end of file, throws.
        final IOException e = assertThrows(IOException.class, reader::read);
        assertEquals(READ_AFTER_EOF, e.getMessage());

        // Closing resets the reader's position back to 0.
        reader.close();
        assertEquals(0, reader.getPosition(), "Available after close");
    }

    @Test
    void testReadCharArray() throws Exception {
        final char[] chars = new char[10];
        final Reader reader = new TestNullReader(15);

        // First read fills the whole 10-element array (positions 0..9).
        final int count1 = reader.read(chars);
        assertEquals(chars.length, count1, "Read 1");
        for (int i = 0; i < count1; i++) {
            assertEquals(i, chars[i], "Check Chars 1");
        }

        // Second read returns only the 5 remaining characters (positions 10..14).
        final int count2 = reader.read(chars);
        assertEquals(5, count2, "Read 2");
        for (int i = 0; i < count2; i++) {
            assertEquals(count1 + i, chars[i], "Check Chars 2");
        }

        // Third read reports end of file (-1).
        final int count3 = reader.read(chars);
        assertEquals(-1, count3, "Read 3 (EOF)");

        // Reading again, now past the end of file, throws.
        final IOException e = assertThrows(IOException.class, () -> reader.read(chars));
        assertEquals(READ_AFTER_EOF, e.getMessage());

        // Closing resets the reader so it can be read from again.
        reader.close();

        // Read into the array using an explicit offset and length.
        final int offset = 2;
        final int length = 4;
        final int count5 = reader.read(chars, offset, length);
        assertEquals(length, count5, "Read 5");
        for (int i = offset; i < length; i++) {
            assertEquals(i, chars[i], "Check Chars 3");
        }
    }

    @Test
    void testSkip() throws Exception {
        try (Reader reader = new TestNullReader(10, true, false)) {
            assertEquals(0, reader.read(), "Read 1");
            assertEquals(1, reader.read(), "Read 2");
            assertEquals(5, reader.skip(5), "Skip 1");
            assertEquals(7, reader.read(), "Read 3");
            assertEquals(2, reader.skip(5), "Skip 2"); // only 2 characters left to skip
            assertEquals(-1, reader.skip(5), "Skip 3 (EOF)"); // end of file reached

            // Skipping again, now past the end of file, throws.
            final IOException e = assertThrows(IOException.class, () -> reader.skip(5));
            assertEquals("Skip after end of file", e.getMessage(), "Skip after EOF IOException message");
        }
    }
}
