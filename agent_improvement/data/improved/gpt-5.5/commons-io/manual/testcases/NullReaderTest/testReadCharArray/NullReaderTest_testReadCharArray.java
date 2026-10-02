package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class NullReaderTest_testReadCharArray {

    private static final int READER_SIZE = 15;
    private static final int BUFFER_SIZE = 10;
    private static final int READ_OFFSET = 2;
    private static final int READ_LENGTH = 4;

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
        final char[] chars = new char[BUFFER_SIZE];
        final Reader reader = new TestNullReader(READER_SIZE);

        final int count1 = reader.read(chars);
        assertEquals(chars.length, count1, "Read 1");
        assertCharacters(chars, 0, count1, 0, "Check Chars 1");

        final int count2 = reader.read(chars);
        assertEquals(5, count2, "Read 2");
        assertCharacters(chars, 0, count2, count1, "Check Chars 2");

        final int count3 = reader.read(chars);
        assertEquals(-1, count3, "Read 3 (EOF)");

        try {
            final int count4 = reader.read(chars);
            fail("Should have thrown an IOException, value=[" + count4 + "]");
        } catch (final IOException e) {
            assertEquals("Read after end of file", e.getMessage());
        }

        reader.close();

        final int count5 = reader.read(chars, READ_OFFSET, READ_LENGTH);
        assertEquals(READ_LENGTH, count5, "Read 5");
        assertCharacters(chars, READ_OFFSET, READ_LENGTH, 0, "Check Chars 3");
    }

    private static void assertCharacters(final char[] chars, final int startIndex, final int endExclusive,
            final int expectedValueOffset, final String message) {
        for (int i = startIndex; i < endExclusive; i++) {
            assertEquals(expectedValueOffset + i, chars[i], message);
        }
    }
}
