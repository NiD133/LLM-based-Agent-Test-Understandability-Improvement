package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class NullReaderTest_testReadCharArray {

    /**
     * A {@link NullReader} that fills the supplied character array with
     * predictable, position-based values so that reads can be verified.
     * <p>
     * For every character read, the value written equals that character's
     * absolute index within the emulated stream (i.e. the first character is
     * 0, the second is 1, and so on).
     * </p>
     */
    private static final class TestNullReader extends NullReader {

        TestNullReader(final int size) {
            super(size);
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

    @Test
    void testReadCharArray() throws Exception {
        // Emulate a stream of 15 characters, read in chunks of 10.
        final int streamSize = 15;
        final char[] buffer = new char[10];
        final Reader reader = new TestNullReader(streamSize);

        // First read: fills the whole 10-char buffer (10 of 15 characters consumed).
        final int firstReadCount = reader.read(buffer);
        assertEquals(buffer.length, firstReadCount, "Read 1");
        for (int i = 0; i < firstReadCount; i++) {
            assertEquals(i, buffer[i], "Check Chars 1");
        }

        // Second read: only 5 characters remain, so only 5 are read.
        final int secondReadCount = reader.read(buffer);
        assertEquals(5, secondReadCount, "Read 2");
        for (int i = 0; i < secondReadCount; i++) {
            assertEquals(firstReadCount + i, buffer[i], "Check Chars 2");
        }

        // Third read: nothing left, so end of file is signalled with -1.
        final int eofReadCount = reader.read(buffer);
        assertEquals(-1, eofReadCount, "Read 3 (EOF)");

        // Reading again after EOF is an error.
        try {
            final int countAfterEof = reader.read(buffer);
            fail("Should have thrown an IOException, value=[" + countAfterEof + "]");
        } catch (final IOException e) {
            assertEquals("Read after end of file", e.getMessage());
        }

        // Closing resets the reader back to its initial position.
        reader.close();

        // Read into a sub-range of the buffer using offset and length.
        final int offset = 2;
        final int length = 4;
        final int offsetReadCount = reader.read(buffer, offset, length);
        assertEquals(length, offsetReadCount, "Read 5");
        for (int i = offset; i < length; i++) {
            assertEquals(i, buffer[i], "Check Chars 3");
        }
    }
}
