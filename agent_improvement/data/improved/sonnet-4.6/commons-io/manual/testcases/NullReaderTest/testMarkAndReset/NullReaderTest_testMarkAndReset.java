package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.Reader;
import org.junit.jupiter.api.Test;

public class NullReaderTest_testMarkAndReset {

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
    void testMarkAndReset() throws Exception {
        final int readLimit = 10;
        final int charsBeforeMark = 3;

        try (Reader reader = new TestNullReader(100, true, false)) {
            assertTrue(reader.markSupported(), "Mark Should be Supported");

            // Resetting with no prior mark should throw IOException
            final IOException noMarkException = assertThrows(IOException.class, reader::reset);
            assertEquals("No position has been marked", noMarkException.getMessage(), "No Mark IOException message");

            // Read some characters before setting the mark
            for (int i = 0; i < charsBeforeMark; i++) {
                assertEquals(i, reader.read(), "Read Before Mark [" + i + "]");
            }

            // Set a mark at the current reader position (charsBeforeMark)
            reader.mark(readLimit);
            final int markPosition = charsBeforeMark;

            // Read a few characters past the mark (still within the read limit)
            for (int i = 0; i < 3; i++) {
                assertEquals(markPosition + i, reader.read(), "Read After Mark [" + i + "]");
            }

            // Reset back to the marked position
            reader.reset();

            // Read readLimit+1 characters from the marked position, which exceeds the read limit
            for (int i = 0; i < readLimit + 1; i++) {
                assertEquals(markPosition + i, reader.read(), "Read After Reset [" + i + "]");
            }

            // Resetting after the read limit has been exceeded should throw IOException
            final IOException readLimitException = assertThrows(IOException.class, reader::reset);
            assertEquals("Marked position [" + markPosition + "] is no longer valid - passed the read limit [" + readLimit + "]",
                    readLimitException.getMessage(), "Read limit IOException message");
        }
    }
}
