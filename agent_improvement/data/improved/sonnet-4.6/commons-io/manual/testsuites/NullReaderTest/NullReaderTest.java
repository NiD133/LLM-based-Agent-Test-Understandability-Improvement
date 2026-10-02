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

    private static final class TestNullReader extends NullReader {
        TestNullReader(final int size) {
            super(size);
        }

        TestNullReader(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        @Override
        protected int processChar() {
            return (int) getPosition() - 1;
        }

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

    @Test
    void testEOFException() throws Exception {
        try (Reader reader = new TestNullReader(2, false, true)) {
            assertEquals(0, reader.read(), "Read 1");
            assertEquals(1, reader.read(), "Read 2");
            assertThrows(EOFException.class, () -> reader.read());
        }
    }

    @Test
    void testMarkAndReset() throws Exception {
        int position = 0;
        final int readLimit = 10;
        try (Reader reader = new TestNullReader(100, true, false)) {
            assertTrue(reader.markSupported(), "Mark Should be Supported");

            // Resetting without a prior mark should throw IOException
            final IOException resetException = assertThrows(IOException.class, reader::reset);
            assertEquals("No position has been marked", resetException.getMessage(), "No Mark IOException message");

            // Advance the position before marking
            for (; position < 3; position++) {
                assertEquals(position, reader.read(), "Read Before Mark [" + position + "]");
            }

            reader.mark(readLimit);

            // Read past the mark to verify the marked position was recorded correctly
            for (int i = 0; i < 3; i++) {
                assertEquals(position + i, reader.read(), "Read After Mark [" + i + "]");
            }

            // Reset returns to the marked position
            reader.reset();

            // Read through and past readLimit to trigger the "limit exceeded" error
            for (int i = 0; i < readLimit + 1; i++) {
                assertEquals(position + i, reader.read(), "Read After Reset [" + i + "]");
            }

            // Resetting after exceeding readLimit should throw IOException
            final IOException limitExceededException = assertThrows(IOException.class, reader::reset);
            assertEquals(
                "Marked position [" + position + "] is no longer valid - passed the read limit [" + readLimit + "]",
                limitExceededException.getMessage(),
                "Read limit IOException message");
        }
    }

    @Test
    void testMarkNotSupported() throws Exception {
        final Reader reader = new TestNullReader(100, false, true);
        assertFalse(reader.markSupported(), "Mark Should NOT be Supported");

        final UnsupportedOperationException markException =
            assertThrows(UnsupportedOperationException.class, () -> reader.mark(5));
        assertEquals(MARK_RESET_NOT_SUPPORTED, markException.getMessage(), "mark() error message");

        final UnsupportedOperationException resetException =
            assertThrows(UnsupportedOperationException.class, reader::reset);
        assertEquals(MARK_RESET_NOT_SUPPORTED, resetException.getMessage(), "reset() error message");

        reader.close();
    }

    @Test
    void testRead() throws Exception {
        final int size = 5;
        final TestNullReader reader = new TestNullReader(size);

        // processChar() returns position - 1, so successive reads return 0, 1, 2, ... size-1
        for (int i = 0; i < size; i++) {
            assertEquals(i, reader.read(), "Check Value [" + i + "]");
        }

        // Reading at EOF returns -1 (throwEofException is false by default)
        assertEquals(-1, reader.read(), "End of File");

        // Reading again after EOF has been signaled throws IOException
        final IOException readAfterEofException = assertThrows(IOException.class, reader::read);
        assertEquals("Read after end of file", readAfterEofException.getMessage());

        // Closing resets position back to zero
        reader.close();
        assertEquals(0, reader.getPosition(), "Available after close");
    }

    @Test
    void testReadCharArray() throws Exception {
        final char[] chars = new char[10];
        final Reader reader = new TestNullReader(15);

        // First read fills the entire 10-element array
        final int firstReadCount = reader.read(chars);
        assertEquals(chars.length, firstReadCount, "Read 1");
        for (int i = 0; i < firstReadCount; i++) {
            assertEquals(i, chars[i], "Check Chars 1");
        }

        // Second read fills only the remaining 5 chars available in the reader
        final int secondReadCount = reader.read(chars);
        assertEquals(5, secondReadCount, "Read 2");
        for (int i = 0; i < secondReadCount; i++) {
            assertEquals(firstReadCount + i, chars[i], "Check Chars 2");
        }

        // Third read reaches EOF and returns -1
        final int eofReadCount = reader.read(chars);
        assertEquals(-1, eofReadCount, "Read 3 (EOF)");

        // Reading again after EOF throws IOException
        final IOException readAfterEofException = assertThrows(IOException.class, () -> reader.read(chars));
        assertEquals("Read after end of file", readAfterEofException.getMessage());

        // reset by closing
        reader.close();

        // Read into a sub-range of the array using offset and length
        final int offset = 2;
        final int lth    = 4;
        final int subRangeReadCount = reader.read(chars, offset, lth);
        assertEquals(lth, subRangeReadCount, "Read 5");
        for (int i = offset; i < lth; i++) {
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
            assertEquals(2, reader.skip(5), "Skip 2"); // only 2 left to skip
            assertEquals(-1, reader.skip(5), "Skip 3 (EOF)"); // End of file

            final IOException skipAfterEofException = assertThrows(IOException.class, () -> reader.skip(5));
            assertEquals("Skip after end of file", skipAfterEofException.getMessage(), "Skip after EOF IOException message");
        }
    }
}
