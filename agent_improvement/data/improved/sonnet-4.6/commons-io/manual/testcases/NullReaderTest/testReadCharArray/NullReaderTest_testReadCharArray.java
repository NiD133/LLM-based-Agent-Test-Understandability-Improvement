package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.Reader;
import org.junit.jupiter.api.Test;

public class NullReaderTest_testReadCharArray {

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

    @Test
    void testReadCharArray() throws Exception {
        final int bufferSize = 10;
        final int readerSize = 15;
        final char[] chars = new char[bufferSize];
        final Reader reader = new TestNullReader(readerSize);

        // First read: fills the entire buffer (10 chars from a 15-char reader)
        final int firstReadCount = reader.read(chars);
        assertEquals(bufferSize, firstReadCount, "Read 1");
        for (int i = 0; i < firstReadCount; i++) {
            assertEquals(i, chars[i], "Check Chars 1");
        }

        // Second read: reads the remaining 5 chars until the reader is exhausted
        final int secondReadCount = reader.read(chars);
        assertEquals(readerSize - bufferSize, secondReadCount, "Read 2");
        for (int i = 0; i < secondReadCount; i++) {
            assertEquals(firstReadCount + i, chars[i], "Check Chars 2");
        }

        // Third read: reader is exhausted, returns -1 (EOF indicator)
        final int eofIndicator = reader.read(chars);
        assertEquals(-1, eofIndicator, "Read 3 (EOF)");

        // Fourth read: reading after EOF must throw IOException
        try {
            final int pastEofResult = reader.read(chars);
            fail("Should have thrown an IOException, value=[" + pastEofResult + "]");
        } catch (final IOException e) {
            assertEquals("Read after end of file", e.getMessage());
        }

        // Closing resets the reader's position back to the beginning
        reader.close();

        // Fifth read: read into a subset of the buffer using offset and length
        final int offset = 2;
        final int length = 4;
        final int offsetReadCount = reader.read(chars, offset, length);
        assertEquals(length, offsetReadCount, "Read 5");
        for (int i = offset; i < length; i++) {
            assertEquals(i, chars[i], "Check Chars 3");
        }
    }
}
