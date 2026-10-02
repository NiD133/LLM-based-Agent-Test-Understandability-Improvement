package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class NullReaderTest_testMarkAndReset {

    private static final int READER_SIZE = 100;
    private static final int MARK_POSITION = 3;
    private static final int READ_LIMIT = 10;

    private static final class TestNullReader extends NullReader {

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
    void testMarkAndReset() throws Exception {
        try (Reader reader = new TestNullReader(READER_SIZE, true, false)) {
            assertTrue(reader.markSupported(), "Mark Should be Supported");

            assertResetFails(reader, "No position has been marked", "No Mark IOException message");

            assertSequentialReads(reader, 0, MARK_POSITION, "Read Before Mark");

            reader.mark(READ_LIMIT);

            assertSequentialReads(reader, MARK_POSITION, 3, "Read After Mark");

            reader.reset();

            assertSequentialReads(reader, MARK_POSITION, READ_LIMIT + 1, "Read After Reset");

            assertResetFails(reader,
                    "Marked position [" + MARK_POSITION + "] is no longer valid - passed the read limit [" + READ_LIMIT + "]",
                    "Read limit IOException message");
        }
    }

    private static void assertResetFails(final Reader reader, final String expectedMessage, final String assertionMessage) {
        final IOException exception = assertThrows(IOException.class, reader::reset);
        assertEquals(expectedMessage, exception.getMessage(), assertionMessage);
    }

    private static void assertSequentialReads(final Reader reader, final int firstExpectedValue, final int readCount,
            final String assertionMessagePrefix) throws IOException {
        for (int i = 0; i < readCount; i++) {
            assertEquals(firstExpectedValue + i, reader.read(), assertionMessagePrefix + " [" + i + "]");
        }
    }
}
