package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class NullReaderTest_testRead {

    /**
     * A NullReader subclass that returns predictable character values:
     * each character read equals its zero-based position index, making
     * assertions easy to verify without any external state.
     */
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
    void testRead() throws Exception {
        final int size = 5;
        final TestNullReader reader = new TestNullReader(size);

        // Each successive read() should return the character at its position index (0-4)
        for (int i = 0; i < size; i++) {
            assertEquals(i, reader.read(), "Check Value [" + i + "]");
        }

        // Once all characters are consumed, read() signals EOF with -1
        assertEquals(-1, reader.read(), "End of File");

        // Calling read() again after EOF (not just at EOF) must throw IOException
        assertThrows(IOException.class, reader::read, "Should have thrown an IOException after end of file");

        // close() resets the reader position back to 0, allowing reuse
        reader.close();
        assertEquals(0, reader.getPosition(), "Available after close");
    }
}
